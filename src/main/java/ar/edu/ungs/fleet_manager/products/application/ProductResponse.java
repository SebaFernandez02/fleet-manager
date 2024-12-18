package ar.edu.ungs.fleet_manager.products.application;

import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderId;

import java.util.List;
import java.math.BigDecimal;


public record ProductResponse(String id,
                              String name,
                              String brand,
                              String description,
                              String category,
                              Integer quantity,
                              String measurement,
                              BigDecimal price,
                              String preferenceProviderId,
                              Integer minStock,
                              String autoPurchase) {
    public static ProductResponse map(Product product) {
        return new ProductResponse(product.id().value(),
                                    product.name().value(),
                                    product.brand().value(),
                                    product.description().value(),
                                    product.category().value(),
                                    product.quantity().value(),
                                    product.measurement().name(),
                                    product.price().value(),
                                    product.preferenceProviderId().value(),
                                    product.minStock().value(),
                                    product.automaticPurchase().name());
    }

    public static List<ProductResponse> map(List<Product> products){
        return products.stream()
                .map(ProductResponse::map)
                .toList();
    }
}
