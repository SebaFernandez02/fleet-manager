package ar.edu.ungs.fleet_manager.analytics.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.analytics.domain.*;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
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
    public List<Analytic> search(EnterpriseId enterpriseId) {
        return List.of(quantity(enterpriseId), avg(enterpriseId), typeCount(enterpriseId), typeStatusCount(enterpriseId), typePriorityCount(enterpriseId), statusCount(enterpriseId), typeAvg(enterpriseId), typeStatusAvg(enterpriseId), typePriorityAvg(enterpriseId), statusAvg(enterpriseId));
    }

    public Analytic quantity(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        count(1) as quantity
                    from controls
                    WHERE enterprise_id = CAST(? AS UUID);
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.CONTROLS,
                            AnalyticType.VALUE,
                            new AnalyticDescription("Total de controles"),
                            value);
    }

    public Analytic avg(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        AVG(date_updated - date_created) as avg
                    from controls
                    WHERE enterprise_id = CAST(? AS UUID);
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.CONTROLS,
                            AnalyticType.VALUE,
                            new AnalyticDescription("Tiempo promedio de resolución"),
                            value);
    }

    public Analytic typeCount(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        type, 
                        count(1) as count
                    from controls
                    WHERE enterprise_id = CAST(? AS UUID)
                    group by type;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.CONTROLS, AnalyticType.BARS, new AnalyticDescription("Cantidad de controles por tipo"), value);
    }

    public Analytic typePriorityCount(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        type, 
                        priority,                         
                        count(1) as count
                    from controls
                    WHERE enterprise_id = CAST(? AS UUID)
                    group by type, priority;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.CONTROLS, AnalyticType.BARS, new AnalyticDescription("Cantidad de controles por tipo y prioridad"), value);
    }

    public Analytic typeStatusCount(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        type, 
                        status, 
                        count(1)
                    from controls
                    WHERE enterprise_id = CAST(? AS UUID)
                    group by type, status;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.CONTROLS, AnalyticType.BARS, new AnalyticDescription("Cantidad de controles por tipo y estado"), value);
    }

    public Analytic statusCount(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        status, 
                        count(1)
                    from controls
                    WHERE enterprise_id = CAST(? AS UUID)
                    group by status;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.CONTROLS, AnalyticType.BARS, new AnalyticDescription("Cantidad de controles por tipo"), value);
    }

    public Analytic typeAvg(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        type, 
                        AVG(date_updated - date_created) as avg
                    from controls
                    where status = 'DONE'
                    and enterprise_id = CAST(? AS UUID)
                    group by type;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.CONTROLS, AnalyticType.BARS, new AnalyticDescription("Promedio de tiempo de resolución de un control por tipo"), value);
    }

    public Analytic typePriorityAvg(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        type, 
                        priority,                         
                        AVG(date_updated - date_created) as avg
                    from controls
                    where status = 'DONE'
                    and enterprise_id = CAST(? AS UUID)
                    group by type, priority;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.CONTROLS, AnalyticType.BARS,new AnalyticDescription("Promedio de tiempo de resolución de un control por tipo y prioridad"), value);
    }

    public Analytic typeStatusAvg(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        type, 
                        status, 
                        AVG(date_updated - date_created) as avg
                    from controls
                    where status = 'DONE'
                    and enterprise_id = CAST(? AS UUID)
                    group by type, status;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.CONTROLS, AnalyticType.BARS,new AnalyticDescription("Promedio de tiempo de resolución de un control por tipo y estado"), value);
    }

    public Analytic statusAvg(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        status, 
                        AVG(date_updated - date_created) as avg
                    from controls
                    where status = 'DONE'
                    and enterprise_id = CAST(? AS UUID)
                    group by status;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.CONTROLS, AnalyticType.BARS, new AnalyticDescription("Promedio de tiempo de resolución de un control por estado"), value);
    }
}
