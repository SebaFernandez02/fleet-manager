package ar.edu.ungs.fleet_manager.reserves.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.reserves.application.update.ReserveStatusUpdater;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PutReserveRestController {
    private final ReserveStatusUpdater updater;

    public PutReserveRestController(ReserveStatusUpdater updater) {
        this.updater = updater;
    }

    @PutMapping("/api/reserves/{id}/status/{status}")
    public ResponseEntity<?> handle(@PathVariable String id, @PathVariable String status) {
        this.updater.execute(id, status);

        return ResponseEntity.ok().build();
    }
}
