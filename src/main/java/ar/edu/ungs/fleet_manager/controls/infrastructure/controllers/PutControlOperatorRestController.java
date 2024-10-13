package ar.edu.ungs.fleet_manager.controls.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.controls.application.update.ControlOperatorUpdater;
import ar.edu.ungs.fleet_manager.users.application.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class PutControlOperatorRestController {
    private final ControlOperatorUpdater updater;

    public PutControlOperatorRestController(ControlOperatorUpdater updater) {
        this.updater = updater;
    }

    @PutMapping ("/api/controls/{id}/operator")
    public ResponseEntity<?> handle(@PathVariable String id, @RequestBody UserRequest operator){
        this.updater.execute(id, operator);

        return ResponseEntity.ok().build();
    }
}
