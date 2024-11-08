package ar.edu.ungs.fleet_manager.analytics.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.analytics.domain.*;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PostgresVehicleAnalyticRepository implements AnalyticRepository {
    private final JdbcTemplate template;

    public PostgresVehicleAnalyticRepository(JdbcTemplate template) {
        this.template = template;
    }


    @Override
    public List<Analytic> search(EnterpriseId enterpriseId) {
        return List.of(quantity(enterpriseId), typeCount(enterpriseId), brandCount(enterpriseId), modelCount(enterpriseId), typeStatusCount(enterpriseId), yearCount(enterpriseId), statusCount(enterpriseId), fuelTypeCount(enterpriseId), axlesCount(enterpriseId), seatsCount(enterpriseId), hasTrailerCount(enterpriseId), loadCount(enterpriseId));
    }

    private Analytic modelCount(EnterpriseId enterpriseId) {
        var sql = """
                    select model,
                        count(1) as quantity
                    from vehicles
                    where enterprise_id = CAST(? AS UUID)
                    group by model;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.VEHICLES,
                AnalyticType.BARS,
                new AnalyticDescription("Total de vehiculos"),
                value);
    }

    public Analytic quantity(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        count(1) as quantity
                    from vehicles
                    where enterprise_id = CAST(? AS UUID);
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.VEHICLES,
                AnalyticType.VALUE,
                new AnalyticDescription("Total de vehiculos"),
                value);
    }


    public Analytic typeCount(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        type, 
                        count(1) as count
                    from vehicles
                    where enterprise_id = CAST(? AS UUID)
                    group by type;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.VEHICLES, AnalyticType.BARS, new AnalyticDescription("Cantidad de vehiculos por tipo"), value);
    }

    public Analytic brandCount(EnterpriseId enterpriseId){
        var sql = """
                    select 
                        brand, 
                        count(1) as count
                    from vehicles
                    where enterprise_id = CAST(? AS UUID)
                    group by brand;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.VEHICLES, AnalyticType.BARS, new AnalyticDescription("Cantidad de vehiculos por marca"), value);

    }

    public Analytic typeStatusCount(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        type, 
                        status, 
                        count(1)
                    from vehicles
                    where enterprise_id = CAST(? AS UUID)
                    group by type, status;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.VEHICLES, AnalyticType.BARS, new AnalyticDescription("Cantidad de vehiculos por tipo y estado"), value);
    }

    public Analytic yearCount(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        year,                       
                        count(1) as count
                    from vehicles
                    where enterprise_id = CAST(? AS UUID)
                    group by year;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.VEHICLES, AnalyticType.BARS, new AnalyticDescription("Cantidad de vehiculos por año"), value);
    }

    public Analytic statusCount(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        status, 
                        count(1)
                    from vehicles
                    where enterprise_id = CAST(? AS UUID)
                    group by status;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.VEHICLES, AnalyticType.PIE, new AnalyticDescription("Cantidad de vehiculos por estado"), value);
    }

    public Analytic fuelTypeCount(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        fuel_type, 
                        count(1)
                    from vehicles
                    where enterprise_id = CAST(? AS UUID)
                    group by fuel_type;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.VEHICLES, AnalyticType.PIE, new AnalyticDescription("Cantidad de vehiculos por tipo de combustible"), value);
    }

    public Analytic axlesCount(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        axles, 
                        count(1)
                    from vehicles
                    where enterprise_id = CAST(? AS UUID)
                    group by axles;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.VEHICLES, AnalyticType.BARS, new AnalyticDescription("Cantidad de vehiculos por cantidad de ejes"), value);
    }

    public Analytic seatsCount(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        seats, 
                        count(1)
                    from vehicles
                    where enterprise_id = CAST(? AS UUID)
                    group by seats;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.VEHICLES, AnalyticType.BARS, new AnalyticDescription("Cantidad de vehiculos por cantidad de asientos"), value);
    }

    public Analytic hasTrailerCount(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        has_trailer, 
                        count(1)
                    from vehicles
                    where enterprise_id = CAST(? AS UUID)
                    group by has_trailer;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.VEHICLES, AnalyticType.PIE, new AnalyticDescription("Cantidad de vehiculos con trailer"), value);
    }

    public Analytic loadCount(EnterpriseId enterpriseId){
        var sql = """
                    select 
                        load, 
                        count(1)
                    from vehicles
                    where enterprise_id = CAST(? AS UUID)
                    group by load;
                """;

        List<Map<String, Object>> value = template.queryForList(sql, enterpriseId.value());

        return new Analytic(AnalyticOrigin.VEHICLES, AnalyticType.BARS, new AnalyticDescription("Cantidad de vehiculos por carga maxima"), value);
    }
}
