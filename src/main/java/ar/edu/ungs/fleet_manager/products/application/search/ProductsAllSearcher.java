package ar.edu.ungs.fleet_manager.products.application.search;

import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import ar.edu.ungs.fleet_manager.products.application.ProductResponse;
import ar.edu.ungs.fleet_manager.products.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ProductsAllSearcher {

    private final ProductRepository repository;

    public ProductsAllSearcher(ProductRepository productRepository) {
        this.repository = productRepository;
    }

    public List<ProductResponse> execute(EnterpriseId enterpriseId){
       return this.repository.searchAll(enterpriseId)
                .stream()
                .map(ProductResponse::map)
                .toList();
    }
}



