package ar.edu.ungs.fleet_manager.controls.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.controls.application.update.ControlStatusUpdater;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class PutControlStatusRestController {
    private final ControlStatusUpdater updater;

    public PutControlStatusRestController(ControlStatusUpdater updater) {
        this.updater = updater;
    }

    @PutMapping ("/api/controls/{id}/{status}")
    public ResponseEntity<?> handle(@PathVariable String id, @PathVariable String status){
        this.updater.execute(id, status);

        return ResponseEntity.ok().build();
    }
}
