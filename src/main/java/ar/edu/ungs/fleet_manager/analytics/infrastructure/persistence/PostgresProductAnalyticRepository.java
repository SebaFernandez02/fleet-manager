package ar.edu.ungs.fleet_manager.analytics.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.analytics.domain.*;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
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
    public List<Analytic> search(EnterpriseId enterpriseId) {
        return List.of(quantity(enterpriseId), brandCount(enterpriseId), categoryCount(enterpriseId), brandCategoryCount(enterpriseId), providerCount(enterpriseId));
    }

    public Analytic quantity(EnterpriseId enterpriseId) {
        var sql = """
                    select 
                        count(1) as quantity
                    from products
                    """ + (enterpriseId != null ? "where enterprise_id = CAST(? AS UUID)" : "") + """
                """;

        List<Map<String, Object>> value = enterpriseId != null ? template.queryForList(sql, enterpriseId.value()) : template.queryForList(sql);

        return new Analytic(AnalyticOrigin.PRODUCTS,
                AnalyticType.VALUE,
                new AnalyticDescription("Total de productos"),
                value);
    }

    public Analytic brandCategoryCount(EnterpriseId enterpriseId) {
        var sql = """
                    select brand, category,
                        count(1) as quantity
                    from products
                    """ + (enterpriseId != null ? "where enterprise_id = CAST(? AS UUID)" : "") + """
                    group by brand, category;
                """;

        List<Map<String, Object>> value = enterpriseId != null ? template.queryForList(sql, enterpriseId.value()) : template.queryForList(sql);

        return new Analytic(AnalyticOrigin.PRODUCTS,
                AnalyticType.BARS,
                new AnalyticDescription("Total de productos por marca y categoria"),
                value);
    }

    public Analytic brandCount(EnterpriseId enterpriseId) {
        var sql = """
                    select brand,
                        count(1) as quantity
                    from products
                    """ + (enterpriseId != null ? "where enterprise_id = CAST(? AS UUID)" : "") + """
                    group by brand;
                """;

        List<Map<String, Object>> value = enterpriseId != null ? template.queryForList(sql, enterpriseId.value()) : template.queryForList(sql);

        return new Analytic(AnalyticOrigin.PRODUCTS,
                AnalyticType.BARS,
                new AnalyticDescription("Total de productos por marca"),
                value);
    }

    public Analytic categoryCount(EnterpriseId enterpriseId) {
        var sql = """
                    select category,
                        count(1) as quantity
                    from products
                    """ + (enterpriseId != null ? "where enterprise_id = CAST(? AS UUID)" : "") + """
                    group by category;
                """;

        List<Map<String, Object>> value = enterpriseId != null ? template.queryForList(sql, enterpriseId.value()) : template.queryForList(sql);

        return new Analytic(AnalyticOrigin.PRODUCTS,
                AnalyticType.BARS,
                new AnalyticDescription("Total de productos por categoria"),
                value);
    }

    public Analytic providerCount(EnterpriseId enterpriseId) {
        var sql = """
                select pr.name as provider_name,
                       COUNT(p.id) as quantity
                from products p
                join providers pr on CAST(p.pref_provider_id AS UUID) = pr.id
                """ + (enterpriseId != null ? "where p.enterprise_id = CAST(? AS UUID)" : "") + """
                group by pr.name;
                """;

        List<Map<String, Object>> value = enterpriseId != null ? template.queryForList(sql, enterpriseId.value()) : template.queryForList(sql);

        return new Analytic(AnalyticOrigin.PRODUCTS,
                AnalyticType.BARS,
                new AnalyticDescription("Cantidad de productos por proveedor"),
                value);
    }

}
