package ar.edu.ungs.fleet_manager.orders.application;

import java.math.BigDecimal;

public record AddOrderProductRequest(String productId, Integer quantity, BigDecimal amount) {
}
