package ar.edu.ungs.fleet_manager.products.application;



public record  ProductRequest(String id, String name, String brand, String description, String category, Integer quantity) {
}
