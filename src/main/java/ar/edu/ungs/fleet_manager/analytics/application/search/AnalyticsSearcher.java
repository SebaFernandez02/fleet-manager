package ar.edu.ungs.fleet_manager.analytics.application.search;

import ar.edu.ungs.fleet_manager.analytics.application.AnalyticResponse;
import ar.edu.ungs.fleet_manager.analytics.domain.AnalyticRepository;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class AnalyticsSearcher {
    private final AnalyticRepository repository;

    public AnalyticsSearcher(AnalyticRepository repository) {
        this.repository = repository;
    }

    public List<AnalyticResponse> execute(EnterpriseId enterpriseId) {
        return this.repository.search(enterpriseId)
                              .stream().map(AnalyticResponse::map)
                              .toList();
    }
}
