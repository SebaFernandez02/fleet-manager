package ar.edu.ungs.fleet_manager.analytics.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.analytics.domain.*;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public final class PostgresAlertAnalyticRepository implements AnalyticRepository {
    private final JdbcTemplate template;

    public PostgresAlertAnalyticRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Analytic> search(EnterpriseId enterpriseId) {
        return List.of(quantity(enterpriseId), avg(enterpriseId), typeCount(enterpriseId), typeStatusCount(enterpriseId), typePriorityCount(enterpriseId), statusCount(enterpriseId), typeAvg(enterpriseId), typeStatusAvg(enterpriseId), typePriorityAvg(enterpriseId), statusAvg(enterpriseId));
    }

    public Analytic quantity(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        count(1) as quantity
                    from alerts
                    where enterprise_id = CAST(? AS UUID)
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.ALERTS,
                AnalyticType.VALUE,
                new AnalyticDescription("Total de alertas"),
                value);
    }

    public Analytic avg(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        AVG(date_updated - date_created) as avg
                    from alerts
                    where enterprise_id = CAST(? AS UUID)
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.ALERTS,
                AnalyticType.VALUE,
                new AnalyticDescription("Tiempo promedio de conocimiento"),
                value);
    }

    public Analytic typeCount(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        title, 
                        count(1) as count
                    from alerts
                    where enterprise_id = CAST(? AS UUID)
                    group by title;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.ALERTS, AnalyticType.BARS, new AnalyticDescription("Cantidad de alertas por tipo"), value);
    }

    public Analytic typeStatusCount(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        title, 
                        acknowledge, 
                        count(1)
                    from alerts
                    where enterprise_id = CAST(? AS UUID)
                    group by title, acknowledge;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.ALERTS, AnalyticType.BARS, new AnalyticDescription("Cantidad de alertas por tipo y conocimiento"), value);
    }

    public Analytic typePriorityCount(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        title, 
                        priority,                         
                        count(1) as count
                    from alerts
                    where enterprise_id = CAST(? AS UUID)
                    group by title, priority;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.ALERTS, AnalyticType.BARS, new AnalyticDescription("Cantidad de alertas por tipo y prioridad"), value);
    }

    public Analytic statusCount(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        acknowledge, 
                        count(1)
                    from alerts
                    where enterprise_id = CAST(? AS UUID)
                    group by acknowledge;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.ALERTS, AnalyticType.BARS, new AnalyticDescription("Cantidad de alertas por conocimiento"), value);
    }

    public Analytic typeAvg(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        title, 
                        AVG(date_updated - date_created) as avg
                    from alerts
                    where acknowledge = true
                    and enterprise_id = CAST(? AS UUID)
                    group by title;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.ALERTS, AnalyticType.BARS, new AnalyticDescription("Promedio de tiempo de resolución de una alerta por tipo"), value);
    }

    public Analytic typeStatusAvg(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        title, 
                        acknowledge, 
                        AVG(date_updated - date_created) as avg
                    from alerts
                    where acknowledge = true
                    and enterprise_id = CAST(? AS UUID)
                    group by title, acknowledge;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.ALERTS, AnalyticType.BARS,new AnalyticDescription("Promedio de tiempo de resolución de una alerta por tipo y conocimiento"), value);

    }

    public Analytic typePriorityAvg(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        title, 
                        priority,                         
                        AVG(date_updated - date_created) as avg
                    from alerts
                    where acknowledge = true
                    and enterprise_id = CAST(? AS UUID)
                    group by title, priority;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.ALERTS, AnalyticType.BARS,new AnalyticDescription("Promedio de tiempo de resolución de una alerta por tipo y prioridad"), value);
    }

    public Analytic statusAvg(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        acknowledge, 
                        AVG(date_updated - date_created) as avg
                    from alerts
                    where acknowledge = true
                    and enterprise_id = CAST(? AS UUID)
                    group by acknowledge;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.ALERTS, AnalyticType.BARS, new AnalyticDescription("Promedio de tiempo de resolución de una alerta por estado"), value);
    }

}
