package ar.edu.ungs.fleet_manager.reserves.application.create;

import ar.edu.ungs.fleet_manager.controls.application.ControlRequest;
import ar.edu.ungs.fleet_manager.controls.application.create.ControlCreator;
import ar.edu.ungs.fleet_manager.controls.domain.ControlStatus;
import ar.edu.ungs.fleet_manager.reserves.application.ReserveRequest;
import ar.edu.ungs.fleet_manager.reserves.domain.Reserve;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveRepository;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveStatus;
import ar.edu.ungs.fleet_manager.reserves.domain.services.ReserveFinder;
import ar.edu.ungs.fleet_manager.reserves.domain.services.ReserveFuelConsumptionCalculator;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import ar.edu.ungs.fleet_manager.trips.domain.TripCalculator;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;
import ar.edu.ungs.fleet_manager.users.domain.User;
import ar.edu.ungs.fleet_manager.users.domain.UserId;
import ar.edu.ungs.fleet_manager.users.domain.services.UserFinder;
import ar.edu.ungs.fleet_manager.vehicles.domain.Coordinates;
import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.VehicleFinder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReserveCreator {
    private final ReserveRepository repository;
    private final VehicleFinder finder;
    private final UserFinder userFinder;
    private final ReserveFinder reserveFinder;
    private final TripCalculator tripCalculator;
    private final ControlCreator controlCreator;
    private final ReserveFuelConsumptionCalculator fuelConsumptionCalculator;

    public ReserveCreator(ReserveRepository repository,
                          VehicleFinder finder,
                          UserFinder userFinder,
                          ReserveFinder reserveFinder,
                          TripCalculator tripCalculator,
                          ControlCreator controlCreator, ReserveFuelConsumptionCalculator fuelConsumptionCalculator) {
        this.repository = repository;
        this.finder = finder;
        this.userFinder = userFinder;
        this.reserveFinder = reserveFinder;
        this.tripCalculator = tripCalculator;
        this.controlCreator = controlCreator;
        this.fuelConsumptionCalculator = fuelConsumptionCalculator;
    }

    public void execute(ReserveRequest request) {
        var user = this.userFinder.execute(new UserId(request.userId()));

        ensureUserIsCustomer(user);

        var vehicle = this.finder.execute(new VehicleId(request.vehicleId()));

        ensureVehicleIsAvailable(vehicle);
        ensureVehicleNotContainsReserve(vehicle);
        ensureUserReservesLimit(user);

        var destination = new Coordinates(request.destination().latitude(), request.destination().longitude());

        var trip = this.tripCalculator.execute(vehicle.coordinates(), destination);

        var fuelConsumption = this.fuelConsumptionCalculator.execute(trip, vehicle);

        var reserve = Reserve.create(vehicle, user, trip, request.dateReserve(), request.dateFinishReserve(), fuelConsumption, request.enterpriseId());

        this.repository.save(reserve);

        this.createControl(vehicle);
    }

    private void ensureVehicleNotContainsReserve(Vehicle vehicle) {
        try {
            reserveFinder.execute(vehicle.id(), vehicle.enterpriseId(), ReserveStatus.CREATED, ReserveStatus.ACTIVATED);

            throw new InvalidParameterException("vehicle contains an reserve");
        } catch (NotFoundException ignored) {

        }
    }

    private void createControl(Vehicle vehicle) {
        ControlRequest request = new ControlRequest("PREVENTIVE",
                                                    "Control preventivo de vehiculo previo a su viaje",
                                                    "Por favor, realizar verificación técnica de forma general",
                                                    vehicle.id().value(), "LOW", null, vehicle.enterpriseId().value());

        this.controlCreator.execute(request);
    }

    private void ensureUserIsCustomer(User user) {
        if (!user.isCustomer()) {
            throw new InvalidParameterException("the user is nos a customer");
        }
    }

    private void ensureVehicleIsAvailable(Vehicle vehicle) {
        if (vehicle.isNotAvailable()) {
            throw new InvalidParameterException("the vehicle is not available");
        }
    }

    private void ensureUserReservesLimit(User user){
        List<Reserve> reserves = this.repository.findByUserId(user.id(), user.enterpriseId().orElseThrow(() -> new NotFoundException("user without enterprise"))).stream().filter(x -> x.status().equals(ReserveStatus.CREATED) || x.status().equals(ReserveStatus.ACTIVATED)).toList();
        if(reserves.size() >= 1){
            throw new InvalidParameterException("the user has reached the active reserves limit");
        }
    }
}
