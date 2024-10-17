package ar.edu.ungs.fleet_manager.alerts.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.alerts.application.AlertResponse;
import ar.edu.ungs.fleet_manager.alerts.application.ack.AlertAckUpdater;
import ar.edu.ungs.fleet_manager.alerts.application.find.AlertByIdFinder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetAlertController {
    private final AlertByIdFinder finder;

    public GetAlertController(AlertByIdFinder finder) {
        this.finder = finder;
    }

    @GetMapping("/api/alerts/{id}")
    public ResponseEntity<?> handle(@PathVariable String id) {
        AlertResponse response = this.finder.execute(id);

        return ResponseEntity.ok(response);
    }
}
