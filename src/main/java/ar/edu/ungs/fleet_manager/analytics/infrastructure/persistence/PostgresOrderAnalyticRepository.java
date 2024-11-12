package ar.edu.ungs.fleet_manager.analytics.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.analytics.domain.*;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public final class PostgresOrderAnalyticRepository implements AnalyticRepository {
    private final JdbcTemplate template;

    public PostgresOrderAnalyticRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Analytic> search(EnterpriseId enterpriseId) {
        return List.of(count(enterpriseId), avg(enterpriseId), countByStatus(enterpriseId), countByProvider(enterpriseId), avgByProvider(enterpriseId));
    }

    private Analytic count(EnterpriseId enterpriseId) {
        var sql = """
                    select
                    	count(1) as quantity
                    from orders
                    """ + (enterpriseId != null ? "where enterprise_id = CAST(? AS UUID)" : "") + """
                """;

        List<Map<String, Object>> value = enterpriseId != null ? template.queryForList(sql, enterpriseId.value()) : template.queryForList(sql);

        return new Analytic(AnalyticOrigin.ORDERS,
                AnalyticType.VALUE,
                new AnalyticDescription("Total de órdenes"),
                value);
    }

    private Analytic avg(EnterpriseId enterpriseId) {
        var sql = """
                    select
                      avg(date_updated - date_created)
                    from orders
                    where status = 'COMPLETED'
                    """ + (enterpriseId != null ? "and enterprise_id = CAST(? AS UUID)" : "") + """
                """;

        List<Map<String, Object>> value = enterpriseId != null ? template.queryForList(sql, enterpriseId.value()) : template.queryForList(sql);

        return new Analytic(AnalyticOrigin.ORDERS,
                AnalyticType.VALUE,
                new AnalyticDescription("Tiempo de resolución promedio de órdenes"),
                value);
    }

    private Analytic countByStatus(EnterpriseId enterpriseId) {
        var sql = """
                    select
                      status,
                      count(1)
                    from orders
                    """ + (enterpriseId != null ? "where enterprise_id = CAST(? AS UUID)" : "") + """
                    group by status;
                """;

        List<Map<String, Object>> value = enterpriseId != null ? template.queryForList(sql, enterpriseId.value()) : template.queryForList(sql);

        return new Analytic(AnalyticOrigin.ORDERS,
                AnalyticType.BARS,
                new AnalyticDescription("Cantidad de órdenes por status"),
                value);
    }

    private Analytic countByProvider(EnterpriseId enterpriseId) {
        var sql = """
                    select
                      p.id,
                      p.name,
                      count(1)
                    from orders o
                      inner join providers p on p.id = o.provider_id
                    """ + (enterpriseId != null ? "where o.enterprise_id = CAST(? AS UUID)" : "") + """
                    group by p.id;
                """;

        List<Map<String, Object>> value = enterpriseId != null ? template.queryForList(sql, enterpriseId.value()) : template.queryForList(sql);

        return new Analytic(AnalyticOrigin.ORDERS,
                AnalyticType.BARS,
                new AnalyticDescription("Cantidad de órdenes por proveedor"),
                value);
    }

    private Analytic avgByProvider(EnterpriseId enterpriseId) {
        var sql = """
                    select
                    	p.id,
                      p.name,
                      avg(date_updated - date_created)
                    from orders o
                      inner join providers p on p.id = o.provider_id
                    where o.status = 'COMPLETED'
                    """ + (enterpriseId != null ? "and o.enterprise_id = CAST(? AS UUID)" : "") + """
                    group by p.id;
                """;

        List<Map<String, Object>> value = enterpriseId != null ? template.queryForList(sql, enterpriseId.value()) : template.queryForList(sql);

        return new Analytic(AnalyticOrigin.ORDERS,
                AnalyticType.BARS,
                new AnalyticDescription("Tiempo de resolución de órdenes por proveedor"),
                value);
    }
}
