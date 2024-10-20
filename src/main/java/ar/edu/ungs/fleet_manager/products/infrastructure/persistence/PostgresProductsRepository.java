package ar.edu.ungs.fleet_manager.products.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.products.domain.ProductRepository;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderId;
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
public final class PostgresProductsRepository implements ProductRepository, RowMapper<Product> {

    private final JdbcTemplate jdbcTemplate;

    public PostgresProductsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Product product) {
        this.findById(product.id()).ifPresentOrElse(x -> this.update(product), () -> this.create(product));
    }

    private void create(Product product) {
        var sql = """
                    insert into products(id, name, brand, category, quantity, description, measurement, price, min_stock, auto_purchase, pref_provider_id)
                    values(CAST(? as UUID), ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        this.jdbcTemplate.update(sql,
                product.id().value(),
                product.name().value(),
                product.brand().value(),
                product.category().value(),
                product.quantity().value(),
                product.description().value(),
                product.measurement().name(),
                product.price().value(),
                product.automaticPurchase().name(),
                product.minStock().value(),
                product.preferenceProviderId().map(ProviderId::value).orElse(null));
    }

    private void update(Product product) {
        var sql = """
                UPDATE products
                SET name = ?, brand = ?, category = ?, quantity = ?, description = ?, measurement = ?, price = ?, pref_provider_id = ?, min_stock = ?, auto_purchase = ?
                WHERE id = CAST(? as UUID)
            """;
        this.jdbcTemplate.update(sql,
                product.name().value(),
                product.brand().value(),
                product.category().value(),
                product.quantity().value(),
                product.description().value(),
                product.measurement().name(),
                product.price().value(),
                product.preferenceProviderId().map(ProviderId::value).orElse(null),
                product.minStock().value(),
                product.automaticPurchase().name(),
                product.id().value());

    }


    @Override
    public Optional<Product> findById(ProductId id) {
        try{
            var sql = """
                        select * from products where id = CAST(? as UUID)
                  
                    """;

            Product result = this.jdbcTemplate.queryForObject(sql, this, id.value());

            return Optional.ofNullable(result);
        }catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Product> searchAll() {
        try{

            var sql = """
                        SELECT * FROM products
                    """;

           return this.jdbcTemplate.query(sql, this);
        }catch (EmptyResultDataAccessException e){
            return Collections.emptyList();

        }

    }

    @Override
    public List<Product> searchAllNoStock() {
        try{

            var sql = """
                            SELECT * FROM products
                            where min_stock > quantity
                        """;

            return this.jdbcTemplate.query(sql, this);
        }catch (EmptyResultDataAccessException e){
            return Collections.emptyList();

        }
    }

    @Override
    public List<Product> searchAllNoStockAutoPurchase() {
        try{

            var sql = """
                            SELECT *\s
                            FROM products p
                            WHERE p.quantity <= p.min_stock
                            and p.pref_provider_id is not null
                            and p.auto_purchase = 'ENABLED'
                            and 0 = (select count(1) from orders o where o.items = concat('%s', p.id, '%s') and o.status in ('CREATED', 'APPROVED'))
                        """;
            return this.jdbcTemplate.query(sql, this);
        }catch (EmptyResultDataAccessException e){
            return Collections.emptyList();

        }
    }

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        var id = rs.getString("id");
        var name = rs.getString("name");
        var brand = rs.getString("brand");
        var description = rs.getString("description");
        var category = rs.getString("category");
        var quantity = rs.getInt("quantity");
        var measurement = rs.getString("measurement");
        var price = rs.getBigDecimal("price");
        var provider_id = rs.getString("pref_provider_id");
        var minStock = rs.getInt("min_stock");
        var autoPurchase = rs.getString("auto_purchase");

        return Product.build(id,name,brand,description,category,quantity, measurement, price, provider_id, minStock, autoPurchase);

    }
}
