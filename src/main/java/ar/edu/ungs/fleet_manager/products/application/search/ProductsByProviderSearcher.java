package ar.edu.ungs.fleet_manager.products.application.search;

import ar.edu.ungs.fleet_manager.products.application.ProductResponse;
import ar.edu.ungs.fleet_manager.products.domain.ProductRepository;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ProductsByProviderSearcher {
    private final ProductRepository repository;

    public ProductsByProviderSearcher(ProductRepository productRepository) {
        this.repository = productRepository;
    }

    public List<ProductResponse> execute(String id){
       return this.repository.searchByProvider(new ProviderId(id))
                .stream()
                .map(ProductResponse::map)
                .toList();
    }
}



