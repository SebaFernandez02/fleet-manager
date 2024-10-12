package ar.edu.ungs.fleet_manager.controls.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.controls.application.ControlRequest;
import ar.edu.ungs.fleet_manager.controls.application.create.ControlCreator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class PostCorrectiveControlRestController {
    private ControlCreator creator;

    public PostCorrectiveControlRestController(ControlCreator creator) {
        this.creator = creator;
    }

    @PostMapping ("/api/controls/corrective")
    public ResponseEntity<?> handle(@RequestBody ControlRequest request){

        this.creator.execute(request, "CORRECTIVE");

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
