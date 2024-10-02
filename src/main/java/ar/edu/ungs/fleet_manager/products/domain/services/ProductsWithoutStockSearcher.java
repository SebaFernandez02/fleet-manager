package ar.edu.ungs.fleet_manager.products.domain.services;

import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.products.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ProductsWithoutStockSearcher {

    private final ProductRepository repository;

    public ProductsWithoutStockSearcher(ProductRepository productRepository) {
        this.repository = productRepository;
    }

    public List<Product> execute(){
        return this.repository.searchAllNoStock();
    }
}