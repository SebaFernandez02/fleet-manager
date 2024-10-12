package ar.edu.ungs.fleet_manager.controls.application.create;

import ar.edu.ungs.fleet_manager.controls.application.ControlRequest;
import ar.edu.ungs.fleet_manager.controls.domain.Control;
import ar.edu.ungs.fleet_manager.controls.domain.ControlRepository;
import org.springframework.stereotype.Component;



@Component
public final class ControlCreator {
    private ControlRepository repository;

    public ControlCreator(ControlRepository repository) {
        this.repository = repository;
    }

    public void execute(ControlRequest request, String type){
        Control control = Control.create(type,
                                        request.subject(),
                                        request.description(),
                                        request.idVehicle());
        this.repository.save(control);

    }
}
