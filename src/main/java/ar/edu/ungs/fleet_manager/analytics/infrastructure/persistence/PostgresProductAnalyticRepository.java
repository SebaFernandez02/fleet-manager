package ar.edu.ungs.fleet_manager.analytics.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.analytics.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PostgresProductAnalyticRepository implements AnalyticRepository {
    private final JdbcTemplate template;

    public PostgresProductAnalyticRepository(JdbcTemplate jdbcTemplate) {
        this.template = jdbcTemplate;
    }

    @Override
    public List<Analytic> search() {
        return List.of(quantity(), brandCount(), categoryCount(), brandCategoryCount(), providerCount());
    }

    public Analytic quantity() {
        var sql = """
                    select 
                        count(1) as quantity
                    from products;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.PRODUCTS,
                AnalyticType.VALUE,
                new AnalyticDescription("Total de productos"),
                value);
    }

    public Analytic brandCategoryCount() {
        var sql = """
                    select brand, category,
                        count(1) as quantity
                    from products
                    group by brand, category;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.PRODUCTS,
                AnalyticType.BARS,
                new AnalyticDescription("Total de productos por marca y categoria"),
                value);
    }

    public Analytic brandCount() {
        var sql = """
                    select brand,
                        count(1) as quantity
                    from products
                    group by brand;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.PRODUCTS,
                AnalyticType.BARS,
                new AnalyticDescription("Total de productos por marca"),
                value);
    }

    public Analytic categoryCount() {
        var sql = """
                    select category,
                        count(1) as quantity
                    from products
                    group by category;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.PRODUCTS,
                AnalyticType.BARS,
                new AnalyticDescription("Total de productos por categoria"),
                value);
    }

    public Analytic providerCount() {
        var sql = """
                select pr.name as provider_name,
                       COUNT(p.id) as quantity
                from products p
                join providers pr on CAST(p.pref_provider_id AS UUID) = pr.id
                group by pr.name;
                """;

        List<Map<String, Object>> value = template.queryForList(sql);

        return new Analytic(AnalyticOrigin.PRODUCTS,
                AnalyticType.BARS,
                new AnalyticDescription("Cantidad de productos por proveedor"),
                value);
    }

}
