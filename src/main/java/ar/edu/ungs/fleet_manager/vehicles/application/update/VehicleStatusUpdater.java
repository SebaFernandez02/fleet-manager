package ar.edu.ungs.fleet_manager.vehicles.application.update;

import ar.edu.ungs.fleet_manager.vehicles.application.VehicleStatusRequest;
import ar.edu.ungs.fleet_manager.vehicles.domain.Coordinates;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleRepository;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleStatus;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.*;
import org.springframework.stereotype.Component;

@Component
public final class VehicleStatusUpdater {
    private final VehicleFinder finder;
    private final VehicleRepository repository;

    public VehicleStatusUpdater(VehicleFinder finder, VehicleRepository repository) {
        this.finder = finder;
        this.repository = repository;
    }

    public void execute(String id, VehicleStatusRequest request) {
       var vehicle = this.finder.execute(new VehicleId(id));

        VehicleStatus statusToUpdate = VehicleStatus.parse(request.status());

        switch(statusToUpdate){
            case RESERVED -> vehicle.reserve();
          //  case RESERVED -> new VehicleReserver(finder,repository).execute(new VehicleId(id));
            case UNAVAILABLE -> vehicle.unavailabler();
            //   case AVAILABLE -> new VehicleReleaser(finder,repository).execute(new VehicleId(id));
            case AVAILABLE -> vehicle.release();
            //  case MAINTENANCE -> new VehicleMaintainer(finder,repository).execute(new VehicleId(id));
            case MAINTENANCE -> vehicle.reserve();
        }
        this.repository.saveStatus(vehicle);
    }
}
