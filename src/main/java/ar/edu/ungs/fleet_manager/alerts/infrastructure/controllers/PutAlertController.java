package ar.edu.ungs.fleet_manager.alerts.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.alerts.application.ack.AlertAckUpdater;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PutAlertController {
    private final AlertAckUpdater ack;

    public PutAlertController(AlertAckUpdater ack) {
        this.ack = ack;
    }

    @PutMapping("/api/alerts/{id}/ack")
    public ResponseEntity<?> handle(@PathVariable String id) {
        this.ack.execute(id);

        return ResponseEntity.ok().build();
    }
}
