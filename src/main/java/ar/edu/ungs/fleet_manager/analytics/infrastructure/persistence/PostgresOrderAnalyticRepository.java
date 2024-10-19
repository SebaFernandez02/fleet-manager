package ar.edu.ungs.fleet_manager.analytics.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.analytics.domain.*;
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
    public List<Analytic> search() {
        return List.of(count(), avg(), countByStatus(), countByProvider(), avgByProvider());
    }

    private Analytic count() {
        var sql = """
                    select
                    	count(1) as quantity
                    from orders;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.ORDERS,
                AnalyticType.VALUE,
                new AnalyticDescription("Total de órdenes"),
                value);
    }

    private Analytic avg() {
        var sql = """
                    select
                      avg(date_updated - date_created)
                    from orders
                    where status = 'COMPLETED';
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.ORDERS,
                AnalyticType.VALUE,
                new AnalyticDescription("Tiempo de resolución promedio de órdenes"),
                value);
    }

    private Analytic countByStatus() {
        var sql = """
                    select
                      status,
                      count(1)
                    from orders
                    group by status;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.ORDERS,
                AnalyticType.BARS,
                new AnalyticDescription("Cantidad de órdenes por status"),
                value);
    }

    private Analytic countByProvider() {
        var sql = """
                    select
                      p.id,
                      p.name,
                      count(1)
                    from orders o
                      inner join providers p on p.id = o.provider_id
                    group by p.id;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.ORDERS,
                AnalyticType.BARS,
                new AnalyticDescription("Cantidad de órdenes por proveedor"),
                value);
    }

    private Analytic avgByProvider() {
        var sql = """
                    select
                    	p.id,
                      p.name,
                      avg(date_updated - date_created)
                    from orders o
                      inner join providers p on p.id = o.provider_id
                    where status = 'COMPLETED'
                    group by p.id;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.ORDERS,
                AnalyticType.BARS,
                new AnalyticDescription("Tiempo de resolución de órdenes por proveedor"),
                value);
    }
}
