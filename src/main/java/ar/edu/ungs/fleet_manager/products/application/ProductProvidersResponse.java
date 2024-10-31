package ar.edu.ungs.fleet_manager.products.application;

import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.providers.application.ProviderResponse;

import java.util.List;


public record ProductProvidersResponse(String id,
                                       String name,
                                       String brand,
                                       String category,
                                       List<ProviderResponse> providers) {
    public static ProductProvidersResponse map(Product product, List<ProviderResponse> providers) {
        return new ProductProvidersResponse(product.id().value(),
                product.name().value(),
                product.brand().value(),
                product.category().value(),
                providers);


    }
}
