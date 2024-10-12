package ar.edu.ungs.fleet_manager.controls.infrastructure.controllers.templates;

import ar.edu.ungs.fleet_manager.controls.application.ControlTemplateResponse;
import ar.edu.ungs.fleet_manager.controls.application.find.ControlTemplateByIdFinder;
import ar.edu.ungs.fleet_manager.controls.domain.ControlTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class GetControlTemplateRestController {
    private final ControlTemplateByIdFinder finder;

    public GetControlTemplateRestController(ControlTemplateByIdFinder finder) {
        this.finder = finder;
    }

    @GetMapping ("/api/controls/templates/{id}")
    public ResponseEntity<ControlTemplateResponse> handle(@PathVariable String id){
        ControlTemplateResponse response = this.finder.execute(id);

        return ResponseEntity.ok(response);
    }
}
