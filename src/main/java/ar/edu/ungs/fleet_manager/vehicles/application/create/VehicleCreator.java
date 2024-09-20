package ar.edu.ungs.fleet_manager.vehicles.application.create;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import ar.edu.ungs.fleet_manager.vehicles.application.VehicleRequest;
import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleRepository;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.VehicleFinder;
import org.springframework.stereotype.Component;

@Component
public final class VehicleCreator {
    private final VehicleRepository repository;
    private final VehicleFinder finder;

    public VehicleCreator(VehicleRepository repository,
                          VehicleFinder finder) {
        this.repository = repository;
        this.finder = finder;
    }

    public void execute(VehicleRequest request) {
        this.ensureVehicleNotExists(request.id());

        Vehicle vehicle = Vehicle.create(request.id(),
                                         request.model(),
                                         request.brand(),
                                         request.year(),
                                         request.coordinates().latitude(),
                                         request.coordinates().longitude());

        this.repository.save(vehicle);
    }

    private void ensureVehicleNotExists(String id) {
        try {
            this.finder.execute(new VehicleId(id));

            throw new InvalidParameterException(String.format("the vehicle %s already exists", id));
        } catch (NotFoundException ignored) {}
    }
}
