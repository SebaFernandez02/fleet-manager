package ar.edu.ungs.fleet_manager.products.domain;

import ar.edu.ungs.fleet_manager.providers.domain.ProviderId;

import java.util.List;
import java.util.Optional;


public interface ProductRepository {
    void save(Product product);

    void addProvider(ProductId productId, ProviderId providerId);

    void deleteProvider(ProductId productId, ProviderId providerId);

    Optional<Product> findById(ProductId id);

    List<Product> searchAll();

    List<Product> searchAllNoStock();

    List<Product> searchAllNoStockAutoPurchase();

}
