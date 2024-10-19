package ar.edu.ungs.fleet_manager.analytics.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.analytics.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public final class PostgresReserveAnalyticRepository implements AnalyticRepository {
    private final JdbcTemplate template;

    public PostgresReserveAnalyticRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Analytic> search() {
        return List.of(quantity(), countByStatus(), countByVehicle(), countByUser());
    }

    public Analytic quantity() {
        var sql = """
                    select count(1) from reserves;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.RESERVES,
                AnalyticType.VALUE,
                new AnalyticDescription("Total de reservas"),
                value);
    }

    public Analytic countByStatus() {
        var sql = """
                    select status, count(1)
                    from reserves
                    group by status;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.RESERVES,
                AnalyticType.PIE,
                new AnalyticDescription("Total de reservas por estado"),
                value);
    }

    public Analytic countByVehicle() {
        var sql = """
                    select
                    	CONCAT(v.brand, ' ', v.model),
                      count(1)
                    from reserves r
                      inner join vehicles v on v.id = r.vehicle_id
                    group by 1;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.RESERVES,
                AnalyticType.PIE,
                new AnalyticDescription("Total de reservas por vehiculo"),
                value);
    }

    public Analytic countByUser() {
        var sql = """
                    select
                     r.user_id,
                     u.username,
                     count(1)
                    from reserves r
                      inner join users u on u.id = r.user_id
                    group by 1, 2;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.RESERVES,
                AnalyticType.PIE,
                new AnalyticDescription("Total de reservas por usuario"),
                value);
    }
}
