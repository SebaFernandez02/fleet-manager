package ar.edu.ungs.fleet_manager.controls.application.update;

import ar.edu.ungs.fleet_manager.controls.domain.Control;
import ar.edu.ungs.fleet_manager.controls.domain.ControlId;
import ar.edu.ungs.fleet_manager.controls.domain.ControlRepository;
import ar.edu.ungs.fleet_manager.controls.domain.ControlStatus;
import ar.edu.ungs.fleet_manager.controls.domain.services.ControlFinder;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;
import org.springframework.stereotype.Component;

@Component
public final class ControlProductDeleter {
    private final ControlRepository repository;
    private final ControlFinder controlFinder;

    public ControlProductDeleter(ControlRepository repository, ControlFinder orderFinder) {
        this.repository = repository;
        this.controlFinder = orderFinder;
    }

    public void execute(String id) {
        Control control = this.controlFinder.execute(new ControlId(id));

        if(control.status().equals(ControlStatus.DONE)){
            throw new InvalidParameterException("The control is already finished");
        }


        control.deleteProducts();

        this.repository.save(control);
    }
}
