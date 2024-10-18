package ar.edu.ungs.fleet_manager.analytics.application.search;

import ar.edu.ungs.fleet_manager.analytics.application.AnalyticResponse;
import ar.edu.ungs.fleet_manager.analytics.domain.AnalyticRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class AnalyticsSearcher {
    private final AnalyticRepository repository;

    public AnalyticsSearcher(AnalyticRepository repository) {
        this.repository = repository;
    }

    public List<AnalyticResponse> execute() {
        return this.repository.search()
                              .stream().map(AnalyticResponse::map)
                              .toList();
    }
}
