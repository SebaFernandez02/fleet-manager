package ar.edu.ungs.fleet_manager.controls.domain.services;

import ar.edu.ungs.fleet_manager.controls.domain.*;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ControlTemplateFinder {
    private final ControlTemplateRepository repository;

    public ControlTemplateFinder(ControlTemplateRepository repository) {
        this.repository = repository;
    }

    public ControlTemplate execute(ControlId id){
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException(String.format("control template %s not found", id.value())));
    }
}
