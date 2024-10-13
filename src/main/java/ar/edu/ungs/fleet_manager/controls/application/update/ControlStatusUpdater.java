package ar.edu.ungs.fleet_manager.controls.application.update;

import ar.edu.ungs.fleet_manager.controls.domain.Control;
import ar.edu.ungs.fleet_manager.controls.domain.ControlId;
import ar.edu.ungs.fleet_manager.controls.domain.ControlRepository;
import ar.edu.ungs.fleet_manager.controls.domain.ControlStatus;
import ar.edu.ungs.fleet_manager.controls.domain.services.ControlFinder;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.VehicleFinder;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.VehicleMaintainer;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.VehicleReleaser;
import org.springframework.stereotype.Component;

@Component
public final class ControlStatusUpdater {
    private final ControlRepository repository;
    private final ControlFinder finder;
    private final VehicleMaintainer vehicleMaintainer;
    private final VehicleReleaser vehicleReleaser;

    public ControlStatusUpdater(ControlRepository repository,
                                ControlFinder finder,
                                VehicleMaintainer vehicleMaintainer,
                                VehicleReleaser vehicleReleaser) {
        this.repository = repository;
        this.finder = finder;
        this.vehicleMaintainer = vehicleMaintainer;
        this.vehicleReleaser = vehicleReleaser;
    }

    public void execute(String id, String status){
        Control control = this.finder.execute(new ControlId(id));

        control.setStatus(ControlStatus.parse(status));

        this.repository.save(control);

        this.solveVehicleStatus(control);
    }

    private void solveVehicleStatus(Control control) {
        switch (control.status()) {
            case DONE -> this.vehicleReleaser.execute(control.vehicleId());
            case TODO, DOING -> this.vehicleMaintainer.execute(control.vehicleId());
        }
    }
}
