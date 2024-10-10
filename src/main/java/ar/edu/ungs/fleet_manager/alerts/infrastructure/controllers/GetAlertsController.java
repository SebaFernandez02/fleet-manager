package ar.edu.ungs.fleet_manager.alerts.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.alerts.application.search.AlertsSearcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetAlertsController {
    private final AlertsSearcher searcher;

    public GetAlertsController(AlertsSearcher searcher) {
        this.searcher = searcher;
    }

    @GetMapping("/api/alerts")
    public ResponseEntity<?> handle() {
        var response = this.searcher.execute();

        return ResponseEntity.ok(response);
    }
}
