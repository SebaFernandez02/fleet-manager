package ar.edu.ungs.fleet_manager.orders.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.orders.domain.Order;
import ar.edu.ungs.fleet_manager.orders.domain.OrderId;
import ar.edu.ungs.fleet_manager.orders.domain.OrderRepository;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public final class PostgresOrderRepository implements OrderRepository, RowMapper<Order>{
    private final JdbcTemplate jdbcTemplate;

    public PostgresOrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Order order) {
        var sql = """
                    insert into orders (id, provider_id, product_id, quantity, amount, date_created, date_updated, status)
                    values (CAST(? as UUID), CAST(? as UUID), CAST(? as UUID), ?, ?, ?, ?, ?)
                  """;
        this.jdbcTemplate.update(sql,
                order.id().value(),
                order.providerId().value(),
                order.productId().value(),
                order.quantity().value(),
                order.amount().value(),
                order.dateCreated(),
                order.dateUpdated(),
                order.status().name());
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        try {
            var sql = """
                select 
                id, 
                provider_id, 
                product_id, 
                quantity,
                amount, 
                date_created, 
                date_updated, 
                status
                from orders o
                where o.id = CAST(? as UUID)
            """;

            Order result = this.jdbcTemplate.queryForObject(sql, this, id.value());

            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Order> searchAll() {
        try {
            var sql = """
                select
                    id,
                    provider_id, 
                    product_id, 
                    quantity,
                    amount, 
                    date_created, 
                    date_updated, 
                    status
                from orders o
            """;

            return this.jdbcTemplate.query(sql, this);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Order> findByProduct(ProductId product) {
        try {
            var sql = """
                select 
                id, 
                provider_id, 
                product_id, 
                quantity,
                amount, 
                date_created, 
                date_updated, 
                status
                from orders o
                where o.product_id = CAST(? as UUID) and status = 'CREATED'
            """;

            Order result = this.jdbcTemplate.queryForObject(sql, this, product.value());

            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        var id = rs.getString("id");
        var providerId = rs.getString("provider_id");
        var productId = rs.getString("product_id");
        var quantity = rs.getInt("quantity");
        var amount = rs.getBigDecimal("amount");
        var dateCreated = rs.getTimestamp("date_created").toLocalDateTime();
        var dateUpdated = rs.getTimestamp("date_updated").toLocalDateTime();
        var status = rs.getString("status");

        return Order.build(id, providerId, productId, quantity, amount, dateCreated, dateUpdated, status);
    }
}