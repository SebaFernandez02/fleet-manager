package ar.edu.ungs.fleet_manager.products.application.search;

import ar.edu.ungs.fleet_manager.products.application.ProductResponse;
import ar.edu.ungs.fleet_manager.products.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ProductsSearchAll {

    private final ProductRepository repository;

    public ProductsSearchAll(ProductRepository productRepository) {
        this.repository = productRepository;
    }

    public List<ProductResponse> execute(){
       return this.repository.searchAll()
                .stream()
                .map(ProductResponse::map)
                .toList();


    }

}



