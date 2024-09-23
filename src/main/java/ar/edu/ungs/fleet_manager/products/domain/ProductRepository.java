package ar.edu.ungs.fleet_manager.products.domain;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


public interface ProductRepository {
    void save(Product product);

    Optional<Product> findById(ProductId id);

    List<Product> searchAll();

}
