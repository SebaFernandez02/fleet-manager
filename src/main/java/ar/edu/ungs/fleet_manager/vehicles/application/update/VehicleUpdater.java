package ar.edu.ungs.fleet_manager.vehicles.application.update;

import ar.edu.ungs.fleet_manager.vehicles.application.VehicleRequest;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleRepository;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.VehicleFinder;
import org.springframework.stereotype.Component;

import static java.util.Optional.*;

@Component
public final class VehicleUpdater {
    private final VehicleFinder finder;
    private final VehicleRepository repository;

    public VehicleUpdater(VehicleFinder finder, VehicleRepository repository) {
        this.finder = finder;
        this.repository = repository;
    }

    public void execute(String id, VehicleRequest request) {
       var vehicle = this.finder.execute(new VehicleId(id));

        ofNullable(request.model()).ifPresent(vehicle::updateModel);
        ofNullable(request.brand()).ifPresent(vehicle::updateBrand);
        ofNullable(request.year()).ifPresent(vehicle::updateYear);
        ofNullable(request.status()).filter(this::isAvailable)
                                    .ifPresent(s -> vehicle.available());

        this.repository.save(vehicle);
    }

    private boolean isAvailable(String status) {
        return status.equals("AVAILABLE");
    }
}
