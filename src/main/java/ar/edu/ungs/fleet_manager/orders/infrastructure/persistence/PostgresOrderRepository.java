package ar.edu.ungs.fleet_manager.orders.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.orders.domain.*;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.shared.infrastructure.persistence.PostgresException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public final class PostgresOrderRepository implements OrderRepository, RowMapper<Order> {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper mapper;

    public PostgresOrderRepository(JdbcTemplate jdbcTemplate, ObjectMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    @Override
    public void save(Order order) {
        this.findById(order.id())
                .ifPresentOrElse(x -> update(order), () -> create(order));
    }

    private void create(Order order) {
        try {
            var sql = """
                      insert into orders (id, provider_id, items, amount, date_created, date_updated, status)
                      values (CAST(? as UUID), CAST(? as UUID), ?, ?, ?, ?, ?)
                    """;

            String itemsJson = mapper.writeValueAsString(order.items().stream().map(OrderProductDto::map).toList());

            this.jdbcTemplate.update(sql,
                    order.id().value(),
                    order.providerId().value(),
                    itemsJson,
                    order.amount().value(),
                    order.dateCreated(),
                    order.dateUpdated(),
                    order.status().name());
        } catch (DataAccessException | JsonProcessingException e) {
            throw new PostgresException(e.getMessage());
        }
    }

    private void update(Order order) throws DataAccessException {

        var sql = """
                  update orders set
                   items = ?, date_created = ?, date_updated = ?, status = ?, provider_id = CAST(? AS UUID)
                  where id = CAST(? AS UUID)
                """;
        try {

            String itemsJson = mapper.writeValueAsString(order.items().stream().map(OrderProductDto::map).toList());

            this.jdbcTemplate.update(sql,
                    itemsJson,
                    order.dateCreated(),
                    order.dateUpdated(),
                    order.status().name(),
                    order.providerId().value(),
                    order.id().value());
        } catch (DataAccessException | JsonProcessingException e) {
            throw new PostgresException(e.getMessage());
        }
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        try {
            var sql = """
                        select *
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
                        select *
                        from orders o
                        order by date_updated desc, date_created desc
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
                        select *
                        from orders o
                        where o.product_id = CAST(? as UUID) and status in ('CREATED', 'APPROVED')
                    """;

            Order result = this.jdbcTemplate.queryForObject(sql, this, product.value());

            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Order mapRow(ResultSet rs, int rowNum) {
        try {
            var id = rs.getString("id");
            var providerId = rs.getString("provider_id");
            var itemsJson = rs.getString("items");
            var amount = rs.getBigDecimal("amount");
            var dateCreated = rs.getTimestamp("date_created").toLocalDateTime();
            var dateUpdated = rs.getTimestamp("date_updated").toLocalDateTime();
            var status = rs.getString("status");
            var products = mapper.readValue(itemsJson, new TypeReference<List<OrderProductDto>>(){}).stream().map(OrderProductDto::map).toList();

            return Order.build(id, providerId, products, amount, dateCreated, dateUpdated, status);
        } catch (SQLException | JsonProcessingException e) {
            throw new PostgresException(e.getMessage());
        }
    }
}
