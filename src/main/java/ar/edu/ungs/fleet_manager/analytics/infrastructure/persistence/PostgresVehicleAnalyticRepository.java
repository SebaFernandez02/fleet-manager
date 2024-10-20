package ar.edu.ungs.fleet_manager.analytics.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.analytics.domain.*;
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
    public List<Analytic> search() {
        return List.of(quantity(), typeCount(), brandCount(), modelCount(), typeStatusCount(), yearCount(), statusCount(), fuelTypeCount(), axlesCount(), seatsCount(), hasTrailerCount(), loadCount());
    }

    private Analytic modelCount() {
        var sql = """
                    select model,
                        count(1) as quantity
                    from vehicles
                    group by model;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.VEHICLES,
                AnalyticType.BARS,
                new AnalyticDescription("Total de vehiculos"),
                value);
    }

    public Analytic quantity() {
        var sql = """
                    select 
                        count(1) as quantity
                    from vehicles;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.VEHICLES,
                AnalyticType.VALUE,
                new AnalyticDescription("Total de vehiculos"),
                value);
    }


    public Analytic typeCount() {
        var sql = """
                    select 
                        type, 
                        count(1) as count
                    from vehicles
                    group by type;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.VEHICLES, AnalyticType.BARS, new AnalyticDescription("Cantidad de vehiculos por tipo"), value);
    }

    public Analytic brandCount(){
        var sql = """
                    select 
                        brand, 
                        count(1) as count
                    from vehicles
                    group by brand;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.VEHICLES, AnalyticType.BARS, new AnalyticDescription("Cantidad de vehiculos por marca"), value);

    }

    public Analytic typeStatusCount() {
        var sql = """
                    select 
                        type, 
                        status, 
                        count(1)
                    from vehicles
                    group by type, status;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.VEHICLES, AnalyticType.BARS, new AnalyticDescription("Cantidad de vehiculos por tipo y estado"), value);
    }

    public Analytic yearCount() {
        var sql = """
                    select 
                        year,                       
                        count(1) as count
                    from vehicles
                    group by year;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.VEHICLES, AnalyticType.BARS, new AnalyticDescription("Cantidad de vehiculos por año"), value);
    }

    public Analytic statusCount() {
        var sql = """
                    select 
                        status, 
                        count(1)
                    from vehicles
                    group by status;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.VEHICLES, AnalyticType.PIE, new AnalyticDescription("Cantidad de vehiculos por estado"), value);
    }

    public Analytic fuelTypeCount() {
        var sql = """
                    select 
                        fuel_type, 
                        count(1)
                    from vehicles
                    group by fuel_type;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.VEHICLES, AnalyticType.PIE, new AnalyticDescription("Cantidad de vehiculos por tipo de combustible"), value);
    }

    public Analytic axlesCount() {
        var sql = """
                    select 
                        axles, 
                        count(1)
                    from vehicles
                    group by axles;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.VEHICLES, AnalyticType.BARS, new AnalyticDescription("Cantidad de vehiculos por cantidad de ejes"), value);
    }

    public Analytic seatsCount() {
        var sql = """
                    select 
                        seats, 
                        count(1)
                    from vehicles
                    group by seats;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.VEHICLES, AnalyticType.BARS, new AnalyticDescription("Cantidad de vehiculos por cantidad de asientos"), value);
    }

    public Analytic hasTrailerCount() {
        var sql = """
                    select 
                        has_trailer, 
                        count(1)
                    from vehicles
                    group by has_trailer;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.VEHICLES, AnalyticType.PIE, new AnalyticDescription("Cantidad de vehiculos con trailer"), value);
    }

    public Analytic loadCount(){
        var sql = """
                    select 
                        load, 
                        count(1)
                    from vehicles
                    group by load;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.VEHICLES, AnalyticType.BARS, new AnalyticDescription("Cantidad de vehiculos por carga maxima"), value);
    }
}
