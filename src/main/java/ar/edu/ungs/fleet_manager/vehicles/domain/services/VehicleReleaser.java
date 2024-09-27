package ar.edu.ungs.fleet_manager.vehicles.domain.services;

import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleRepository;
import org.springframework.stereotype.Component;

@Component
public final class VehicleReleaser {
    private final VehicleFinder finder;
    private final VehicleRepository repository;

    public VehicleReleaser(VehicleFinder finder, VehicleRepository repository) {
        this.finder = finder;
        this.repository = repository;
    }

    public void execute(VehicleId id) {
        var vehicle = this.finder.execute(id);

        if (vehicle.isAvailable()) {
            return;
        }

        vehicle.release();

        this.repository.save(vehicle);
    }
}
