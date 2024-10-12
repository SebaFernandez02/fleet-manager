package ar.edu.ungs.fleet_manager.controls.application.find;

import ar.edu.ungs.fleet_manager.controls.application.ControlTemplateResponse;
import ar.edu.ungs.fleet_manager.controls.domain.ControlId;
import ar.edu.ungs.fleet_manager.controls.domain.ControlTemplate;
import ar.edu.ungs.fleet_manager.controls.domain.ControlTemplateRepository;
import ar.edu.ungs.fleet_manager.controls.domain.services.ControlTemplateFinder;
import org.springframework.stereotype.Component;

@Component
public final class ControlTemplateByIdFinder {
    private ControlTemplateFinder finder;

    public ControlTemplateByIdFinder(ControlTemplateFinder finder) {
        this.finder = finder;
    }

    public ControlTemplateResponse execute(String id){

        ControlTemplate template = this.finder.execute(new ControlId(id));

        return ControlTemplateResponse.map(template);
    }
}
