package ar.edu.ungs.fleet_manager.controls.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.controls.domain.ControlProduct;
import ar.edu.ungs.fleet_manager.orders.domain.Quantity;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;

public record ControlProductDto(String productId, Integer quantity) {
    public static ControlProductDto map(ControlProduct orderProduct) {
        return new ControlProductDto(orderProduct.productId().value(), orderProduct.quantity().value());
    }

    public static ControlProduct map(ControlProductDto orderProduct) {
        return new ControlProduct(new ProductId(orderProduct.productId()), new Quantity(orderProduct.quantity()));
    }
}
