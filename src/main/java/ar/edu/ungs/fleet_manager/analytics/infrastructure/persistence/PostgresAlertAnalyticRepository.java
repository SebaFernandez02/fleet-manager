package ar.edu.ungs.fleet_manager.analytics.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.analytics.domain.Analytic;
import ar.edu.ungs.fleet_manager.analytics.domain.AnalyticRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class PostgresAlertAnalyticRepository implements AnalyticRepository {
    private final JdbcTemplate template;

    public PostgresAlertAnalyticRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Analytic> search() {
        return List.of();
    }
}
