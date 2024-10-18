package ar.edu.ungs.fleet_manager.analytics.domain;

import java.util.List;
import java.util.Map;

public record Analytic(AnalyticOrigin origin,
                       AnalyticType type,
                       AnalyticDescription description,
                       List<Map<String, Object>> values) {
}
