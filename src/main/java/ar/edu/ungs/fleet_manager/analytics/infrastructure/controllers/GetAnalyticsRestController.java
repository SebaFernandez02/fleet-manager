package ar.edu.ungs.fleet_manager.analytics.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.analytics.application.AnalyticResponse;
import ar.edu.ungs.fleet_manager.analytics.application.search.AnalyticsSearcher;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public final class GetAnalyticsRestController {
    private final AnalyticsSearcher searcher;

    public GetAnalyticsRestController(AnalyticsSearcher searcher) {
        this.searcher = searcher;
    }

    @GetMapping("/api/analytics")
    public ResponseEntity<?> handle(@RequestParam("enterprise_id") String enterpriseId) {
        List<AnalyticResponse> values = this.searcher.execute(new EnterpriseId(enterpriseId));

        return ResponseEntity.ok(values);
    }
}
