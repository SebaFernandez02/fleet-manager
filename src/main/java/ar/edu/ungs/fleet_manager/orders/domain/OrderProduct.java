package ar.edu.ungs.fleet_manager.orders.domain;

import ar.edu.ungs.fleet_manager.products.domain.ProductId;

import java.math.BigDecimal;

public record OrderProduct(ProductId productId, Quantity quantity, BigDecimal amount) {
}
