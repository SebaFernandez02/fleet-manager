package ar.edu.ungs.fleet_manager.orders.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.orders.domain.*;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.shared.infrastructure.persistence.PostgresException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


@Component
public final class PostgresOrderRepository implements OrderRepository, RowMapper<Order> {
    private final JdbcTemplate jdbcTemplate;


    public PostgresOrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public void save(Order order) {
        this.findById(order.id())
                .ifPresentOrElse(x -> update(order), () -> create(order));
    }

    private void create(Order order) {
        try {
            var sql = """
                      insert into orders (id, provider_id, products, amount, date_created, date_updated, status)
                      values (CAST(? as UUID), CAST(? as UUID), ?::jsonb, ?, ?, ?, ?)
                    """;
//            var productos = order.products();
//            String productsJson = JsonConfig.createJsonMapper().writeValueAsString(order.products());

            String productsJson = "{}";


            this.jdbcTemplate.update(sql,
                    order.id().value(),
                    order.providerId().value(),
                    productsJson,
                    order.amount().value(),
                    order.dateCreated(),
                    order.dateUpdated(),
                    order.status().name());
        } catch (DataAccessException e) {
            throw new PostgresException(e);
        }
    }

    private void update(Order order) throws DataAccessException {

        var sql = """
                  update orders set
                   products = products || ?::jsonb, date_created = ?, date_updated = ?, status = ?, provider_id = CAST(? AS UUID)
                  where id = CAST(? AS UUID)
                """;
        try {

            //   var productos = order.products();
            //   String productsJson = JsonConfig.createJsonMapper().writeValueAsString(order.products());
            String productsJson = order.products().stream()
                    .map(product -> String.format(
                            "{\"id\": \"%s\", \"quantity\": %d, \"amount\": %s}",
                            product.productId().value(),
                            product.quantity().value(),
                            product.amount().toPlainString()
                    ))
                    .reduce((acc, json) -> acc + "," + json)
                    .map(result -> "[" + result + "]")
                    .orElse("[]");

            this.jdbcTemplate.update(sql,
                    productsJson,
                    order.dateCreated(),
                    order.dateUpdated(),
                    order.status().name(),
                    order.providerId().value(),
                    order.id().value());
        } catch (DataAccessException e) {
            throw new PostgresException(e);
        }
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        try {
            var sql = """
                        select
                        id,
                        provider_id,
                        products,
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
                            amount,
                            products,
                            date_created,
                            date_updated,
                            status
                        from orders o
                        where o.products is not null
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
            var productsJson = rs.getString("products");
            var amount = rs.getBigDecimal("amount");
            var dateCreated = rs.getTimestamp("date_created").toLocalDateTime();
            var dateUpdated = rs.getTimestamp("date_updated").toLocalDateTime();
            var status = rs.getString("status");


            var  products = parseProductsFromJson(productsJson);




            //var products = objectMapper.readValue(productsJson, new TypeReference<List<OrderProduct>>(){});
            return Order.build(id, providerId, products, amount, dateCreated, dateUpdated, status);
        } catch (SQLException e) {
            throw new PostgresException(e);
        }
    }

    private List<OrderProduct> parseProductsFromJson(String productsJson) {
        productsJson = productsJson.trim();

        if (productsJson.equals("[]") || productsJson.equals("{}")) {
            return List.of();
        }

        productsJson = productsJson.substring(1, productsJson.length() - 1);
        String[] productsArray = productsJson.split("\\},\\{");

        List<OrderProduct> productList = new ArrayList<>();

        for (String product : productsArray) {
            product = product.replace("{", "").replace("}", "").trim();

            String[] keyValuePairs = product.split(",\\s*");

            String id = null;
            Integer quantity = null;
            BigDecimal amount = null;

            for (String pair : keyValuePairs) {
                String[] entry = pair.split(":\\s*");

                if (entry.length == 2) {
                    String key = entry[0].replace("\"", "").trim();
                    String value = entry[1].replace("\"", "").trim();

                    switch (key) {
                        case "id":
                            id = value;
                            break;
                        case "quantity":
                            quantity = Integer.valueOf(value);
                            break;
                        case "amount":
                            amount = new BigDecimal(value);
                            break;
                    }
                }
            }

            if (id != null && quantity != null) {
                productList.add(new OrderProduct(new ProductId(id), new Quantity(quantity), amount));


            }
        }
            return productList;
        }
    }
