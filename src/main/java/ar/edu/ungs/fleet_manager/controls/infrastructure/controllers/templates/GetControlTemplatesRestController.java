package ar.edu.ungs.fleet_manager.controls.infrastructure.controllers.templates;

import ar.edu.ungs.fleet_manager.controls.application.ControlTemplateResponse;
import ar.edu.ungs.fleet_manager.controls.application.search.ControlTemplateAllSearcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetControlTemplatesRestController {
    private final ControlTemplateAllSearcher searcher;

    public GetControlTemplatesRestController(ControlTemplateAllSearcher searcher) {
        this.searcher = searcher;
    }

    @GetMapping ("/api/controls/templates")
    public ResponseEntity<List<ControlTemplateResponse>> handle(){

        List<ControlTemplateResponse> templates = this.searcher.execute();

        return ResponseEntity.ok(templates);

    }
}
