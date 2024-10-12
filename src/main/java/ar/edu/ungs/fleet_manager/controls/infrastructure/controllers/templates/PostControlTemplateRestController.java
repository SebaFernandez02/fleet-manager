package ar.edu.ungs.fleet_manager.controls.infrastructure.controllers.templates;

import ar.edu.ungs.fleet_manager.controls.application.ControlTemplateRequest;
import ar.edu.ungs.fleet_manager.controls.application.create.ControlTemplateCreator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostControlTemplateRestController {
    private final ControlTemplateCreator creator;

    public PostControlTemplateRestController(ControlTemplateCreator creator) {
        this.creator = creator;
    }

    @PostMapping ("/api/controls/templates")
    public ResponseEntity<?> handle(@RequestBody ControlTemplateRequest request){
        this.creator.execute(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
