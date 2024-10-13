package ar.edu.ungs.fleet_manager.controls.application.update;

import ar.edu.ungs.fleet_manager.controls.domain.Control;
import ar.edu.ungs.fleet_manager.controls.domain.ControlId;
import ar.edu.ungs.fleet_manager.controls.domain.ControlRepository;
import ar.edu.ungs.fleet_manager.controls.domain.services.ControlFinder;
import ar.edu.ungs.fleet_manager.users.application.UserRequest;
import ar.edu.ungs.fleet_manager.users.domain.User;
import ar.edu.ungs.fleet_manager.users.domain.UserId;
import ar.edu.ungs.fleet_manager.users.domain.Username;
import ar.edu.ungs.fleet_manager.users.domain.services.UserFinder;
import org.springframework.stereotype.Component;

@Component
public final class ControlOperatorUpdater {
    private final ControlRepository repository;
    private final ControlFinder controlFinder;
    private final UserFinder userFinder;

    public ControlOperatorUpdater(ControlRepository repository, ControlFinder controlFinder, UserFinder userFinder) {
        this.repository = repository;
        this.controlFinder = controlFinder;
        this.userFinder = userFinder;
    }


    public void execute(String id, UserRequest operator) {
        User user = this.userFinder.execute(new Username(operator.username()));

        Control control = this.controlFinder.execute(new ControlId(id));

        control.setOperatorId(user.id());

        this.repository.save(control);
    }
}