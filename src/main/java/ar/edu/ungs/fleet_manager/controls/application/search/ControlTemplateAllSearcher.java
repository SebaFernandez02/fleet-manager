package ar.edu.ungs.fleet_manager.controls.application.search;

import ar.edu.ungs.fleet_manager.controls.application.ControlResponse;
import ar.edu.ungs.fleet_manager.controls.application.ControlTemplateResponse;
import ar.edu.ungs.fleet_manager.controls.domain.Control;
import ar.edu.ungs.fleet_manager.controls.domain.ControlTemplate;
import ar.edu.ungs.fleet_manager.controls.domain.ControlTemplateRepository;
import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ControlTemplateAllSearcher {
    private ControlTemplateRepository repository;

    public ControlTemplateAllSearcher(ControlTemplateRepository repository) {
        this.repository = repository;
    }

    public List<ControlTemplateResponse> execute(){
        return this.repository.searchAll().stream().map(this::apply).toList();
    }

    private ControlTemplateResponse apply (ControlTemplate control){

        return ControlTemplateResponse.map(control);
    }
}
