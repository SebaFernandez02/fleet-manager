package ar.edu.ungs.fleet_manager.vehicles.application.find;

import ar.edu.ungs.fleet_manager.vehicles.application.VehicleResponse;
import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.VehicleFinder;
import org.springframework.stereotype.Component;

@Component
public final class VehicleByIdFinder {
    private final VehicleFinder finder;

    public VehicleByIdFinder(VehicleFinder finder) {
        this.finder = finder;
    }

    public VehicleResponse execute(String id) {
        Vehicle vehicle = this.finder.execute(new VehicleId(id));

        return VehicleResponse.map(vehicle);
    }
}
