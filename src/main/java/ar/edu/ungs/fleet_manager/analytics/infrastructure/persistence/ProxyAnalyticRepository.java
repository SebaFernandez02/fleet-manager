package ar.edu.ungs.fleet_manager.analytics.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.analytics.domain.Analytic;
import ar.edu.ungs.fleet_manager.analytics.domain.AnalyticOrigin;
import ar.edu.ungs.fleet_manager.analytics.domain.AnalyticRepository;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Primary
@Component
public class ProxyAnalyticRepository implements AnalyticRepository {
    private final Map<AnalyticOrigin, AnalyticRepository> strategies;

    public ProxyAnalyticRepository(Map<AnalyticOrigin, AnalyticRepository> strategies) {
        this.strategies = strategies;
    }

    @Override
    public List<Analytic> search(EnterpriseId enterpriseId) {
        return Arrays.stream(AnalyticOrigin.values())
                     .filter(strategies::containsKey)
                     .map(strategies::get)
                     .map((AnalyticRepository repository) -> repository.search(enterpriseId))
                     .flatMap(List::stream)
                     .toList();
    }
}
