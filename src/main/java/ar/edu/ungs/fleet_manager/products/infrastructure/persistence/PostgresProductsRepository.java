package ar.edu.ungs.fleet_manager.products.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.products.domain.ProductRepository;
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
        var sql = """
                    insert into products(id, name, brand, description, category, quantity)
                    values(CAST(? as UUID), ?, ?, ?, ?, ?)
                """;
        this.jdbcTemplate.update(sql, product.id().value()
        ,       product.name().value(),
                product.brand().value(),
                product.category().value(),
                product.quantity(),
                product.description().value());
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
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        var id = rs.getString("id");
        var name = rs.getString("name");
        var brand = rs.getString("brand");
        var description = rs.getString("description");
        var category = rs.getString("category");
        var quantity = rs.getInt("quantity");

        return Product.build(id,name,brand,description,category,quantity);

    }
}
