package ar.edu.ungs.fleet_manager.analytics.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.analytics.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PostgresControlAnalyticRepository implements AnalyticRepository {
    private final JdbcTemplate template;

    public PostgresControlAnalyticRepository(JdbcTemplate template) {
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
                    from controls;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.CONTROLS,
                            AnalyticType.VALUE,
                            new AnalyticDescription("Total de controles"),
                            value);
    }

    public Analytic avg() {
        var sql = """
                    select 
                        AVG(date_updated - date_created) as avg
                    from controls;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.CONTROLS,
                            AnalyticType.VALUE,
                            new AnalyticDescription("Tiempo promedio de resolución"),
                            value);
    }

    public Analytic typeCount() {
        var sql = """
                    select 
                        type, 
                        count(1) as count
                    from controls
                    group by type;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.CONTROLS, AnalyticType.BARS, new AnalyticDescription("Cantidad de controles por tipo"), value);
    }

    public Analytic typePriorityCount() {
        var sql = """
                    select 
                        type, 
                        priority,                         
                        count(1) as count
                    from controls
                    group by type, priority;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.CONTROLS, AnalyticType.BARS, new AnalyticDescription("Cantidad de controles por tipo y prioridad"), value);
    }

    public Analytic typeStatusCount() {
        var sql = """
                    select 
                        type, 
                        status, 
                        count(1)
                    from controls
                    group by type, status;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.CONTROLS, AnalyticType.BARS, new AnalyticDescription("Cantidad de controles por tipo y estado"), value);
    }

    public Analytic statusCount() {
        var sql = """
                    select 
                        status, 
                        count(1)
                    from controls
                    group by status;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.CONTROLS, AnalyticType.BARS, new AnalyticDescription("Cantidad de controles por tipo"), value);
    }

    public Analytic typeAvg() {
        var sql = """
                    select 
                        type, 
                        AVG(date_updated - date_created) as avg
                    from controls
                    where status = 'DONE'
                    group by type;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.CONTROLS, AnalyticType.BARS, new AnalyticDescription("Promedio de tiempo de resolución de un control por tipo"), value);
    }

    public Analytic typePriorityAvg() {
        var sql = """
                    select 
                        type, 
                        priority,                         
                        AVG(date_updated - date_created) as avg
                    from controls
                    where status = 'DONE'
                    group by type, priority;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.CONTROLS, AnalyticType.BARS,new AnalyticDescription("Promedio de tiempo de resolución de un control por tipo y prioridad"), value);
    }

    public Analytic typeStatusAvg() {
        var sql = """
                    select 
                        type, 
                        status, 
                        AVG(date_updated - date_created) as avg
                    from controls
                    where status = 'DONE'
                    group by type, status;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.CONTROLS, AnalyticType.BARS,new AnalyticDescription("Promedio de tiempo de resolución de un control por tipo y estado"), value);
    }

    public Analytic statusAvg() {
        var sql = """
                    select 
                        status, 
                        AVG(date_updated - date_created) as avg
                    from controls
                    where status = 'DONE'
                    group by status;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.CONTROLS, AnalyticType.BARS, new AnalyticDescription("Promedio de tiempo de resolución de un control por estado"), value);
    }
}
