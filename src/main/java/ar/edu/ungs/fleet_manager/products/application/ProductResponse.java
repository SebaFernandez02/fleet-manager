package ar.edu.ungs.fleet_manager.products.application;

import ar.edu.ungs.fleet_manager.products.domain.Product;

import java.math.BigDecimal;


public record ProductResponse(String id,
                              String name,
                              String brand,
                              String description,
                              String category,
                              Integer quantity,
                              String measurement,
                              BigDecimal price) {



    public static ProductResponse map(Product product) {

        return new ProductResponse(product.id().value(),
                                    product.name().value(),
                                    product.brand().value(),
                                    product.description().value(),
                                    product.category().value(),
                                    product.quantity().value(),
                                    product.measurement().name(),
                                    product.price().value());
    }
}
