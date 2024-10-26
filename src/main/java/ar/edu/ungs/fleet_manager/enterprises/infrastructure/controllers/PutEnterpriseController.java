package ar.edu.ungs.fleet_manager.enterprises.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.enterprises.application.EnterpriseRequest;
import ar.edu.ungs.fleet_manager.enterprises.application.create.EnterpriseCreator;
import ar.edu.ungs.fleet_manager.enterprises.application.update.EnterpriseUpdater;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PutEnterpriseController {
    private final EnterpriseUpdater updater;

    public PutEnterpriseController(EnterpriseUpdater updater) {
        this.updater = updater;
    }

    @PutMapping("/api/enterprises/{id}")
    public ResponseEntity<?> handle(@PathVariable String id, @RequestBody EnterpriseRequest request) {
        this.updater.execute(id, request);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
