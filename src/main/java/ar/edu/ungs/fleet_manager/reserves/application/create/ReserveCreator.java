package ar.edu.ungs.fleet_manager.reserves.application.create;

import ar.edu.ungs.fleet_manager.reserves.application.ReserveRequest;
import ar.edu.ungs.fleet_manager.reserves.domain.Reserve;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveRepository;
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

@Component
public class ReserveCreator {
    private final ReserveRepository repository;
    private final VehicleFinder finder;
    private final UserFinder userFinder;
    private final TripCalculator tripCalculator;

    public ReserveCreator(ReserveRepository repository, VehicleFinder finder, UserFinder userFinder, TripCalculator tripCalculator) {
        this.repository = repository;
        this.finder = finder;
        this.userFinder = userFinder;
        this.tripCalculator = tripCalculator;
    }

    public void execute(ReserveRequest request) {
        var user = this.userFinder.execute(new UserId(request.userId()));

        ensureUserIsCustomer(user);

        var vehicle = this.finder.execute(new VehicleId(request.vehicleId()));

        ensureVehicleIsAvailable(vehicle);

        var destination = new Coordinates(request.destination().latitude(), request.destination().longitude());

        var trip = this.tripCalculator.execute(vehicle.coordinates(), destination);

        var reserve = Reserve.create(vehicle, user, trip);

        this.repository.save(reserve);
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
}
