package ar.edu.ungs.fleet_manager.controls.application.find;

import ar.edu.ungs.fleet_manager.controls.application.ControlResponse;
import ar.edu.ungs.fleet_manager.controls.domain.Control;
import ar.edu.ungs.fleet_manager.controls.domain.ControlId;
import ar.edu.ungs.fleet_manager.controls.domain.services.ControlFinder;
import ar.edu.ungs.fleet_manager.users.domain.User;
import ar.edu.ungs.fleet_manager.users.domain.services.UserFinder;
import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.VehicleFinder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class ControlByIdFinder {
    private final ControlFinder controlFinder;
    private final VehicleFinder vehicleFinder;
    private final UserFinder userFinder;

    public ControlByIdFinder(ControlFinder controlFinder, VehicleFinder vehicleFinder, UserFinder userFinder) {
        this.controlFinder = controlFinder;
        this.vehicleFinder = vehicleFinder;
        this.userFinder = userFinder;
    }

    public ControlResponse execute(String id) {
        Control control = this.controlFinder.execute(new ControlId(id));
        Vehicle vehicle = this.vehicleFinder.execute(control.vehicleId());
        User user = Optional.ofNullable(control.operatorId()).isPresent() ? this.userFinder.execute(control.operatorId()) : null;

        return ControlResponse.map(control, vehicle, user);
    }
}
