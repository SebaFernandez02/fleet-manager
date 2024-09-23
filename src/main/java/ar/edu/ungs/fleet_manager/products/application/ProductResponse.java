package ar.edu.ungs.fleet_manager.products.application;

import ar.edu.ungs.fleet_manager.products.domain.Product;

import java.time.LocalDateTime;

public record ProductResponse(String id,
                              String name,
                              String brand,
                              String category,
                              LocalDateTime purchaseDate) {



    public static ProductResponse map(Product product) {

        return new ProductResponse(product.id().value(),
                                    product.name().value(),
                                    product.brand().value(),
                                    product.category().value(),
                                    product.purchaseDate());
    }
}
