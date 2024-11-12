package ar.edu.ungs.fleet_manager.analytics.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.analytics.domain.*;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
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
    public List<Analytic> search(EnterpriseId enterpriseId) {
        return List.of(quantity(enterpriseId), countByStatus(enterpriseId), countByVehicle(enterpriseId), countByUser(enterpriseId));
    }

    public Analytic quantity(EnterpriseId enterpriseId) {
        var sql = """
                    select count(1) 
                    from reserves
                    """ + (enterpriseId != null ? "where enterprise_id = CAST(? AS UUID)" : "") + """
                """;

        List<Map<String, Object>> value = enterpriseId != null ? template.queryForList(sql, enterpriseId.value()) : template.queryForList(sql);

        return new Analytic(AnalyticOrigin.RESERVES,
                AnalyticType.VALUE,
                new AnalyticDescription("Total de reservas"),
                value);
    }

    public Analytic countByStatus(EnterpriseId enterpriseId) {
        var sql = """
                    select status, count(1)
                    from reserves
                    """ + (enterpriseId != null ? "where enterprise_id = CAST(? AS UUID)" : "") + """
                    group by status;
                """;

        List<Map<String, Object>> value = enterpriseId != null ? template.queryForList(sql, enterpriseId.value()) : template.queryForList(sql);

        return new Analytic(AnalyticOrigin.RESERVES,
                AnalyticType.PIE,
                new AnalyticDescription("Total de reservas por estado"),
                value);
    }

    public Analytic countByVehicle(EnterpriseId enterpriseId) {
        var sql = """
                    select
                    	CONCAT(v.brand, ' ', v.model),
                      count(1)
                    from reserves r
                      inner join vehicles v on v.id = r.vehicle_id
                    """ + (enterpriseId != null ? "where r.enterprise_id = CAST(? AS UUID)" : "") + """
                    group by 1;
                """;

        List<Map<String, Object>> value = enterpriseId != null ? template.queryForList(sql, enterpriseId.value()) : template.queryForList(sql);

        return new Analytic(AnalyticOrigin.RESERVES,
                AnalyticType.PIE,
                new AnalyticDescription("Total de reservas por vehiculo"),
                value);
    }

    public Analytic countByUser(EnterpriseId enterpriseId) {
        var sql = """
                    select
                     r.user_id,
                     u.username,
                     count(1)
                    from reserves r
                      inner join users u on u.id = r.user_id
                    """ + (enterpriseId != null ? "where r.enterprise_id = CAST(? AS UUID)" : "") + """
                    group by 1, 2;
                """;

        List<Map<String, Object>> value = enterpriseId != null ? template.queryForList(sql, enterpriseId.value()) : template.queryForList(sql);

        return new Analytic(AnalyticOrigin.RESERVES,
                AnalyticType.PIE,
                new AnalyticDescription("Total de reservas por usuario"),
                value);
    }
}
