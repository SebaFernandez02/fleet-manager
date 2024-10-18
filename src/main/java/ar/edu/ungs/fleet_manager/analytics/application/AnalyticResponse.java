package ar.edu.ungs.fleet_manager.analytics.application;

import ar.edu.ungs.fleet_manager.analytics.domain.Analytic;

import java.util.List;
import java.util.Map;

public record AnalyticResponse(String origin,
                               String type,
                               String description,
                               List<Map<String, Object>> values) {
    public static AnalyticResponse map(Analytic analytic) {
        return new AnalyticResponse(analytic.origin().name(),
                                    analytic.type().name(),
                                    analytic.description().value(),
                                    analytic.values());
    }
}
