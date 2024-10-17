package ar.edu.ungs.fleet_manager.orders.application;

import ar.edu.ungs.fleet_manager.products.application.ProductResponse;

public record OrderProductResponse(ProductResponse product, Integer quantity) {
}
