package ar.edu.ungs.fleet_manager.orders.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.orders.domain.Order;
import ar.edu.ungs.fleet_manager.orders.domain.OrderId;
import ar.edu.ungs.fleet_manager.orders.domain.OrderRepository;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.shared.infrastructure.persistence.PostgresException;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


@Component
public final class PostgresOrderRepository implements OrderRepository, RowMapper<Order>{
    private final JdbcTemplate jdbcTemplate;

    public PostgresOrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override


    public void save(Order order) {
        this.findById(order.id())
                .ifPresentOrElse(x -> update(order), () -> create(order));
    }

    private void create(Order order)  {
        try {
            var sql = """
                      insert into orders (id, provider_id, products, amount, date_created, date_updated, status)
                      values (CAST(? as UUID), CAST(? as UUID), ?::jsonb, ?, ?, ?, ?)
                    """;

            String productsJson = order.products().entrySet().stream()
                    .map(entry -> String.format(
                            "{\"id\": \"%s\", \"quantity\": %d}",
                            entry.getKey().value(), entry.getValue().value()
                    ))
                    .reduce((acc, json) -> acc + "," + json) // Esto combina todos los elementos del stream en un solo resultado
                    .map(result -> "[" + result + "]")
                    .orElse("[]");

            this.jdbcTemplate.update(sql,
                    order.id().value(),
                    order.providerId().value(),
                    productsJson,
                    order.amount().value(),
                    order.dateCreated(),
                    order.dateUpdated(),
                    order.status().name());
        } catch (NestedRuntimeException e) {
            throw new PostgresException(e);
        }
    }
    private void update(Order order) throws DataAccessException {

        var sql = """
                    update orders set
                    quantity = ?, amount = ?, date_created = ?, date_updated = ?, status = ?, provider_id = CAST(? AS UUID), 
                    products = products || ?::jsonb
                    where id = CAST(? AS UUID)
                  """;
        try {

            String productsJson = order.products().entrySet().stream()
                    .map(entry -> String.format(
                            "{\"id\": \"%s\", \"quantity\": %d}",
                            entry.getKey().value(), entry.getValue().value()
                    ))
                    .reduce((acc, json) -> acc + "," + json)
                    .map(result -> "[" + result + "]")
                    .orElse("[]");

            this.jdbcTemplate.update(sql,
                    productsJson,
                    order.amount().value(),
                    order.dateCreated(),
                    order.dateUpdated(),
                    order.status().name(),
                    order.providerId().value(),
                    order.id().value());
        }catch(DataAccessException e){

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
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {
            var id = rs.getString("id");
            var providerId = rs.getString("provider_id");
            var productsJson= rs.getString("products");
            var amount = rs.getBigDecimal("amount");
            var dateCreated = rs.getTimestamp("date_created").toLocalDateTime();
            var dateUpdated = rs.getTimestamp("date_updated").toLocalDateTime();
            var status = rs.getString("status");

            var products = parseProductsFromJson(productsJson);

            return Order.build(id, providerId, products, amount, dateCreated, dateUpdated, status);
        }catch (SQLException e){
            throw new PostgresException(e.getMessage());
        }

    }

    private Map<String, Integer> parseProductsFromJson(String productsJson) {


        productsJson = productsJson.trim();
        if (productsJson.equals("[]") || productsJson.equals("{}")) {
            return Collections.emptyMap();
        }

        productsJson = productsJson.substring(1, productsJson.length() - 1);

        Map<String, Integer> productMap = new HashMap<>();

        String[] productsArray = productsJson.split("\\},\\{");

        for (String product : productsArray) {
            // Limpiar los corchetes
            product = product.replace("{", "").replace("}", "").trim();

            // Dividir en pares clave-valor
            String[] keyValuePairs = product.split(",\\s*");

            String id = null;
            Integer quantity = null;

            for (String pair : keyValuePairs) {
                String[] entry = pair.split(":\\s*");
                if (entry.length == 2) {
                    String key = entry[0].replace("\"", "").trim();
                    String value = entry[1].trim();

                    if ("id".equals(key)) {
                        id = value;
                    } else if ("quantity".equals(key)) {
                        quantity = Integer.valueOf(value);
                    }
                }
            }

            if (id != null && quantity != null) {
                productMap.put(id, quantity);
            }
        }

        return productMap;
    }



}