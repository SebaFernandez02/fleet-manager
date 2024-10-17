package ar.edu.ungs.fleet_manager.orders.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.orders.domain.OrderProduct;
import ar.edu.ungs.fleet_manager.orders.domain.Quantity;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;

import java.math.BigDecimal;

public record OrderProductDto(String productId, Integer quantity, BigDecimal amount) {
    public static OrderProductDto map(OrderProduct orderProduct) {
        return new OrderProductDto(orderProduct.productId().value(), orderProduct.quantity().value(), orderProduct.amount());
    }

    public static OrderProduct map(OrderProductDto orderProduct) {
        return new OrderProduct(new ProductId(orderProduct.productId()), new Quantity(orderProduct.quantity()), orderProduct.amount);
    }
}
