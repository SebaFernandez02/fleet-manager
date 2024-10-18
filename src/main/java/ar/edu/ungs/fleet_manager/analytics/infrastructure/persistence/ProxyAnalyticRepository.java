package ar.edu.ungs.fleet_manager.analytics.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.analytics.domain.Analytic;
import ar.edu.ungs.fleet_manager.analytics.domain.AnalyticOrigin;
import ar.edu.ungs.fleet_manager.analytics.domain.AnalyticRepository;
import ar.edu.ungs.fleet_manager.analytics.domain.AnalyticType;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Primary
@Component
public class ProxyAnalyticRepository implements AnalyticRepository, RowMapper<Analytic> {
    private final Map<AnalyticOrigin, AnalyticRepository> strategies;

    public ProxyAnalyticRepository(Map<AnalyticOrigin, AnalyticRepository> strategies) {
        this.strategies = strategies;
    }

    @Override
    public List<Analytic> search() {
        return Arrays.stream(AnalyticOrigin.values())
                     .filter(strategies::containsKey)
                     .map(strategies::get)
                     .map(AnalyticRepository::search)
                     .flatMap(List::stream)
                     .toList();
    }

    @Override
    public Analytic mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;
    }
}
