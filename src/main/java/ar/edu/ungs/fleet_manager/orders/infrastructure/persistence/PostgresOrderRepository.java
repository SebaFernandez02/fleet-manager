package ar.edu.ungs.fleet_manager.orders.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.orders.domain.Order;
import ar.edu.ungs.fleet_manager.orders.domain.OrderId;
import ar.edu.ungs.fleet_manager.orders.domain.OrderProduct;
import ar.edu.ungs.fleet_manager.orders.domain.OrderRepository;
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
                    insert into orders (id, providerId, productId, quantity, amount, date_created, date_updated, status)
                    values (CAST(? as UUID), ?, ?, ?, ?, ?, ?, ?)
                  """;
        this.jdbcTemplate.update(sql,
                order.id().value(),
                order.provider().value(),
                order.product().value(),
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
                providerId, 
                productId, 
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
                    providerId,
                    productId,
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
    public Optional<Order> findByProduct(OrderProduct product) {
        try {
            var sql = """
                select 
                id, 
                providerId, 
                productId, 
                quantity,
                amount, 
                date_created, 
                date_updated, 
                status
                from orders o
                where o.productId = ? and status = 'ACTIVE'
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
        var provider = rs.getString("providerId");
        var product = rs.getString("productId");
        var quantity = rs.getInt("quantity");
        var amount = rs.getInt("amount");
        var dateCreated = rs.getTimestamp("date_created").toLocalDateTime();
        var dateUpdated = rs.getTimestamp("date_updated").toLocalDateTime();
        var status = rs.getString("status");

        return Order.build(id, provider, product, quantity, amount, dateCreated, dateUpdated, status);
    }
}