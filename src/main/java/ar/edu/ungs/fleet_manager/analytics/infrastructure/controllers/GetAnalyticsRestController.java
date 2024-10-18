package ar.edu.ungs.fleet_manager.analytics.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.analytics.application.AnalyticResponse;
import ar.edu.ungs.fleet_manager.analytics.application.search.AnalyticsSearcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public final class GetAnalyticsRestController {
    private final AnalyticsSearcher searcher;

    public GetAnalyticsRestController(AnalyticsSearcher searcher) {
        this.searcher = searcher;
    }

    @GetMapping("/api/analytics")
    public ResponseEntity<?> handle() {
        List<AnalyticResponse> values = this.searcher.execute();

        return ResponseEntity.ok(values);
    }
}
