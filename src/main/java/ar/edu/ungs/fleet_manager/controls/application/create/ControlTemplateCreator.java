package ar.edu.ungs.fleet_manager.controls.application.create;

import ar.edu.ungs.fleet_manager.controls.application.ControlTemplateRequest;
import ar.edu.ungs.fleet_manager.controls.domain.ControlTemplate;
import ar.edu.ungs.fleet_manager.controls.domain.ControlTemplateRepository;
import org.springframework.stereotype.Component;

@Component
public final class ControlTemplateCreator {
    private ControlTemplateRepository repository;

    public ControlTemplateCreator(ControlTemplateRepository repository) {
        this.repository = repository;
    }

    public void execute (ControlTemplateRequest request){
        ControlTemplate template = ControlTemplate.create(request.subject(), request.description(), request.priority(), request.assigned());

        this.repository.save(template);
    }
}
