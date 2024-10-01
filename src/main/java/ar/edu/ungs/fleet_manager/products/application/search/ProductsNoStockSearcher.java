package ar.edu.ungs.fleet_manager.products.application.search;

import ar.edu.ungs.fleet_manager.products.application.ProductResponse;
import ar.edu.ungs.fleet_manager.products.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ProductsNoStockSearcher {

    private final ProductRepository repository;

    public ProductsNoStockSearcher(ProductRepository productRepository) {
        this.repository = productRepository;
    }

    public List<ProductResponse> execute(){
        return this.repository.searchAllNoStock()
                .stream()
                .map(ProductResponse::map)
                .toList();
    }
}