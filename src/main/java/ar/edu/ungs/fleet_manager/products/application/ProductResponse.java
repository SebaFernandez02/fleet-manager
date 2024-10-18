package ar.edu.ungs.fleet_manager.products.application;

import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderId;
import ar.edu.ungs.fleet_manager.providers.domain.services.ProviderFinder;

import java.util.List;
import java.math.BigDecimal;
import java.util.Optional;

public record ProductResponse(String id,
                              String name,
                              String brand,
                              String description,
                              String category,
                              Integer quantity,
                              String measurement,
                              BigDecimal price,
                              String pref_provider) {
    public static ProductResponse map(Product product) {
        return new ProductResponse(product.id().value(),
                                    product.name().value(),
                                    product.brand().value(),
                                    product.description().value(),
                                    product.category().value(),
                                    product.quantity().value(),
                                    product.measurement().name(),
                                    product.price().value(),
                                    product.prefProvider()
                                            .map(ProviderId::value)
                                            .orElse(""));
    }

    public static List<ProductResponse> map(List<Product> products){
        return products.stream()
                .map(ProductResponse::map)
                .toList();
    }
}
