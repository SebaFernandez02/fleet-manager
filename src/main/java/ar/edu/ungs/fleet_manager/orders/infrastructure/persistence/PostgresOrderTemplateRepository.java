package ar.edu.ungs.fleet_manager.orders.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.orders.domain.OrderTemplate;
import ar.edu.ungs.fleet_manager.orders.domain.OrderTemplateRepository;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class PostgresOrderTemplateRepository implements OrderTemplateRepository, RowMapper<OrderTemplate> {
    private final JdbcTemplate jdbcTemplate;

    public PostgresOrderTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(OrderTemplate order) {
        var sql = """
                    insert into order_templates (product_id , provider_id, quantity, amount)
                    values (CAST(? as UUID), CAST(? as UUID), ?, ?)
                  """;
        this.jdbcTemplate.update(sql,
                order.product(),
                order.provider(),
                order.quantity(),
                order.amount());
    }



    @Override
    public Optional<OrderTemplate> findByProduct(ProductId product) {
        try {
            var sql = """
                select 
                product_id, 
                provider_id, 
                quantity,
                amount, 
                from order_templates o
                where o.product_id = CAST(? as UUID)
            """;

            OrderTemplate result = this.jdbcTemplate.queryForObject(sql, this, product.value());

            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public OrderTemplate mapRow(ResultSet rs, int rowNum) throws SQLException {
        var providerId = rs.getString("provider_id");
        var productId = rs.getString("product_id");
        var quantity = rs.getInt("quantity");
        var amount = rs.getBigDecimal("amount");

        return new OrderTemplate(providerId, productId, quantity, amount);
    }
}
