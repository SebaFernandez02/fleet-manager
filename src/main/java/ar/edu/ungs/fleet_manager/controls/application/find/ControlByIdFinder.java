package ar.edu.ungs.fleet_manager.controls.application.find;

import ar.edu.ungs.fleet_manager.controls.application.ControlRequest;
import ar.edu.ungs.fleet_manager.controls.application.ControlResponse;
import ar.edu.ungs.fleet_manager.controls.domain.Control;
import ar.edu.ungs.fleet_manager.controls.domain.ControlId;
import ar.edu.ungs.fleet_manager.controls.domain.ControlRepository;
import ar.edu.ungs.fleet_manager.controls.domain.services.ControlFinder;
import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.VehicleFinder;
import org.springframework.stereotype.Component;

@Component
public final class ControlByIdFinder {
    private final ControlFinder controlFinder;
    private final VehicleFinder vehicleFinder;

    public ControlByIdFinder(ControlFinder controlFinder, VehicleFinder vehicleFinder) {
        this.controlFinder = controlFinder;
        this.vehicleFinder = vehicleFinder;
    }

    public ControlResponse execute (String id){
        Control control = this.controlFinder.execute(new ControlId(id));
        Vehicle vehicle = this.vehicleFinder.execute(control.idVehicle());

        return ControlResponse.map(control, vehicle);
    }
}
