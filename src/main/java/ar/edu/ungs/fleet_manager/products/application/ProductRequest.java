package ar.edu.ungs.fleet_manager.products.application;


import java.math.BigDecimal;

public record ProductRequest(String name, String brand, String description, String category, Integer quantity, String measurement, BigDecimal price, String providerId, Integer minStock, Boolean autoPurchase) {
}
