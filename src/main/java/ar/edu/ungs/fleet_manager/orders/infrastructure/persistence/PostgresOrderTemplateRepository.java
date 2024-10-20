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
    public void save(OrderTemplate template) {
        this.findByProduct(
                template.productId()).ifPresentOrElse(this::update, () -> this.create(template));
    }

    private void update(OrderTemplate template) {
        var sql = """
            update order_template
            set provider_id = CAST(? as UUID),
                quantity = ?,
                amount = ?
            where product_id = CAST(? as UUID)
          """;

        this.jdbcTemplate.update(sql, template.providerId().value(),
                                      template.quantity().value(),
                                      template.amount().value(),
                                      template.productId().value());
    }

    private void create(OrderTemplate template) {
        var sql = """
                    insert into order_template (product_id , provider_id, quantity, amount)
                    values (CAST(? as UUID), CAST(? as UUID), ?, ?)
                  """;
        this.jdbcTemplate.update(sql, template.productId().value(),
                                      template.productId().value(),
                                      template.quantity().value(),
                                      template.amount().value());
    }


    @Override
    public Optional<OrderTemplate> findByProduct(ProductId product) {
        try {
            var sql = """
            select
                product_id,
                provider_id,
                amount,
                quantity
            from order_template o
            where product_id = CAST(? AS UUID)
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

        return OrderTemplate.build(providerId, productId, quantity, amount);
    }
}
