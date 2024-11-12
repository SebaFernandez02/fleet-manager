package ar.edu.ungs.fleet_manager.orders.application;

import ar.edu.ungs.fleet_manager.products.application.ProductResponse;

import java.math.BigDecimal;

public record OrderProductResponse(ProductResponse product, Integer quantity, BigDecimal amount) {
}
