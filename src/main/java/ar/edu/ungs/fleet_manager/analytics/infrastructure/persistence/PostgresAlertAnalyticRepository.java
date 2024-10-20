package ar.edu.ungs.fleet_manager.analytics.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.analytics.domain.*;
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
    public List<Analytic> search() {
        return List.of(quantity(), avg(), typeCount(), typeStatusCount(), typePriorityCount(), statusCount(), typeAvg(), typeStatusAvg(), typePriorityAvg(), statusAvg());
    }

    public Analytic quantity() {
        var sql = """
                    select 
                        count(1) as quantity
                    from alerts;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.ALERTS,
                AnalyticType.VALUE,
                new AnalyticDescription("Total de alertas"),
                value);
    }

    public Analytic avg() {
        var sql = """
                    select 
                        AVG(date_updated - date_created) as avg
                    from alerts;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.ALERTS,
                AnalyticType.VALUE,
                new AnalyticDescription("Tiempo promedio de conocimiento"),
                value);
    }

    public Analytic typeCount() {
        var sql = """
                    select 
                        title, 
                        count(1) as count
                    from alerts
                    group by title;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.ALERTS, AnalyticType.BARS, new AnalyticDescription("Cantidad de alertas por tipo"), value);
    }

    public Analytic typeStatusCount() {
        var sql = """
                    select 
                        title, 
                        acknowledge, 
                        count(1)
                    from alerts
                    group by title, acknowledge;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.ALERTS, AnalyticType.BARS, new AnalyticDescription("Cantidad de alertas por tipo y conocimiento"), value);
    }

    public Analytic typePriorityCount() {
        var sql = """
                    select 
                        title, 
                        priority,                         
                        count(1) as count
                    from alerts
                    group by title, priority;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.ALERTS, AnalyticType.BARS, new AnalyticDescription("Cantidad de alertas por tipo y prioridad"), value);
    }

    public Analytic statusCount() {
        var sql = """
                    select 
                        acknowledge, 
                        count(1)
                    from alerts
                    group by acknowledge;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.ALERTS, AnalyticType.BARS, new AnalyticDescription("Cantidad de alertas por conocimiento"), value);
    }

    public Analytic typeAvg() {
        var sql = """
                    select 
                        title, 
                        AVG(date_updated - date_created) as avg
                    from alerts
                    where acknowledge = true
                    group by title;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.ALERTS, AnalyticType.BARS, new AnalyticDescription("Promedio de tiempo de resolución de una alerta por tipo"), value);
    }

    public Analytic typeStatusAvg() {
        var sql = """
                    select 
                        title, 
                        acknowledge, 
                        AVG(date_updated - date_created) as avg
                    from alerts
                    where acknowledge = true
                    group by title, acknowledge;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.ALERTS, AnalyticType.BARS,new AnalyticDescription("Promedio de tiempo de resolución de una alerta por tipo y conocimiento"), value);

    }

    public Analytic typePriorityAvg() {
        var sql = """
                    select 
                        title, 
                        priority,                         
                        AVG(date_updated - date_created) as avg
                    from alerts
                    where acknowledge = true
                    group by title, priority;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.ALERTS, AnalyticType.BARS,new AnalyticDescription("Promedio de tiempo de resolución de una alerta por tipo y prioridad"), value);
    }

    public Analytic statusAvg() {
        var sql = """
                    select 
                        acknowledge, 
                        AVG(date_updated - date_created) as avg
                    from alerts
                    where acknowledge = true
                    group by acknowledge;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.ALERTS, AnalyticType.BARS, new AnalyticDescription("Promedio de tiempo de resolución de una alerta por estado"), value);
    }

}
