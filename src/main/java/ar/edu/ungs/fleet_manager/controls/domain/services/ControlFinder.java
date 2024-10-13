package ar.edu.ungs.fleet_manager.controls.domain.services;

import ar.edu.ungs.fleet_manager.controls.domain.Control;
import ar.edu.ungs.fleet_manager.controls.domain.ControlId;
import ar.edu.ungs.fleet_manager.controls.domain.ControlRepository;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ControlFinder {
    private final ControlRepository repository;

    public ControlFinder(ControlRepository repository) {
        this.repository = repository;
    }

    public Control execute(ControlId id){
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException(String.format("control %s not found", id.value())));
    }
}
