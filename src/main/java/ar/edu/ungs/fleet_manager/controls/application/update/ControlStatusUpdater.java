package ar.edu.ungs.fleet_manager.controls.application.update;

import ar.edu.ungs.fleet_manager.controls.domain.Control;
import ar.edu.ungs.fleet_manager.controls.domain.ControlId;
import ar.edu.ungs.fleet_manager.controls.domain.ControlRepository;
import ar.edu.ungs.fleet_manager.controls.domain.ControlStatus;
import ar.edu.ungs.fleet_manager.controls.domain.services.ControlFinder;
import org.springframework.stereotype.Component;

@Component
public final class ControlStatusUpdater {
    private final ControlRepository repository;
    private final ControlFinder finder;

    public ControlStatusUpdater(ControlRepository repository, ControlFinder finder) {
        this.repository = repository;
        this.finder = finder;
    }

    public void execute(String id, String status){
        ControlStatus statusToUpdate = ControlStatus.parse(status);

        Control control = this.finder.execute(new ControlId(id));

        control.setStatus(statusToUpdate);

        this.repository.save(control);
    }
}
