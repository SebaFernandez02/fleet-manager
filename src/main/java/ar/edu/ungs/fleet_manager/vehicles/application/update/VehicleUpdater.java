package ar.edu.ungs.fleet_manager.vehicles.application.update;

import ar.edu.ungs.fleet_manager.alerts.domain.AlertStrategy;
import ar.edu.ungs.fleet_manager.alerts.domain.services.AlertCreator;
import ar.edu.ungs.fleet_manager.reserves.domain.Reserve;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveStatus;
import ar.edu.ungs.fleet_manager.reserves.domain.services.ReserveFinder;
import ar.edu.ungs.fleet_manager.shared.aplication.CoordinatesRequest;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import ar.edu.ungs.fleet_manager.trips.domain.TripOutOfRangeValidator;
import ar.edu.ungs.fleet_manager.vehicles.application.VehicleRequest;
import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleRepository;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.VehicleFinder;
import org.springframework.stereotype.Component;

import static java.util.Optional.*;

@Component
public final class VehicleUpdater {
    private final VehicleFinder finder;
    private final VehicleRepository repository;
    private final ReserveFinder reserveFinder;
    private final TripOutOfRangeValidator tripOutOfRangeValidator;
    private final AlertCreator alertCreator;

    public VehicleUpdater(VehicleFinder finder, VehicleRepository repository, ReserveFinder reserveFinder, TripOutOfRangeValidator tripOutOfRangeValidator, AlertCreator alertCreator) {
        this.finder = finder;
        this.repository = repository;
        this.reserveFinder = reserveFinder;
        this.tripOutOfRangeValidator = tripOutOfRangeValidator;
        this.alertCreator = alertCreator;
    }

    public void execute(String id, VehicleRequest request) {
       var vehicle = this.finder.execute(new VehicleId(id));

        ofNullable(request.model()).ifPresent(vehicle::updateModel);
        ofNullable(request.brand()).ifPresent(vehicle::updateBrand);
        ofNullable(request.year()).ifPresent(vehicle::updateYear);
        ofNullable(request.type()).ifPresent(vehicle::updateType);
        ofNullable(request.color()).ifPresent(vehicle::updateColor);
        ofNullable(request.fuelType()).ifPresent(vehicle::updateFuelType);
        ofNullable(request.fuelMeasurement()).ifPresent(vehicle::updateFuelMeasurement);
        ofNullable(request.fuelConsumption()).ifPresent(vehicle::updateFuelConsumption);
        ofNullable(request.cantAxles()).ifPresent(vehicle::updateAxles);
        ofNullable(request.cantSeats()).ifPresent(vehicle::updateSeats);
        ofNullable(request.load()).ifPresent(vehicle::updateLoad);
        of(request.hasTrailer()).ifPresent(vehicle::updateHasTrailer);
        ofNullable(request.status()).filter(this::isAvailable)
                                    .ifPresent(s -> vehicle.available());
        ofNullable(request.coordinates()).ifPresent(coordinatesRequest -> solveCoordinates(vehicle, coordinatesRequest));

        this.repository.save(vehicle);
    }

    private void solveCoordinates(Vehicle vehicle, CoordinatesRequest coordinatesRequest) {
        vehicle.updateCoordinates(coordinatesRequest.latitude(), coordinatesRequest.longitude());

        try {
            Reserve reserve = reserveFinder.execute(vehicle.id(), vehicle.enterpriseId(), ReserveStatus.ACTIVATED);

            if (this.tripOutOfRangeValidator.execute(reserve.trip(), vehicle.coordinates())) {
                this.alertCreator.execute(AlertStrategy.OUT_OF_RANGE, vehicle.id().value(), vehicle.enterpriseId().value());
            }
        } catch (NotFoundException ignored) {

        }
    }

    private boolean isAvailable(String status) {
        return status.equals("AVAILABLE");
    }
}
