package ar.edu.ungs.fleet_manager.controls.application;

import ar.edu.ungs.fleet_manager.products.application.ProductResponse;

public record ControlProductResponse(ProductResponse product, Integer quantity) {
}
