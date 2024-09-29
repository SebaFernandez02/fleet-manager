package ar.edu.ungs.fleet_manager.vehicles.application.update;

import ar.edu.ungs.fleet_manager.vehicles.application.VehicleInfoRequest;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleRepository;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleStatus;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.VehicleFinder;
import org.springframework.stereotype.Component;

@Component
public final class VehicleInfoUpdater {
    private final VehicleFinder finder;
    private final VehicleRepository repository;

    public VehicleInfoUpdater(VehicleFinder finder, VehicleRepository repository) {
        this.finder = finder;
        this.repository = repository;
    }

    public void execute(String id, VehicleInfoRequest request) {
       var vehicle = this.finder.execute(new VehicleId(id));

        vehicle.updateInfo(request);

        this.repository.save(vehicle);
    }
}
