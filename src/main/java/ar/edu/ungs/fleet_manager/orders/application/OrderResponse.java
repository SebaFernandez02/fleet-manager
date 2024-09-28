package ar.edu.ungs.fleet_manager.orders.application;

import ar.edu.ungs.fleet_manager.orders.domain.Order;
import ar.edu.ungs.fleet_manager.products.application.ProductResponse;
import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.providers.application.ProviderResponse;
import ar.edu.ungs.fleet_manager.providers.domain.Provider;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse (String id,
                             ProviderResponse provider,
                             ProductResponse product,
                             Integer quantity,
                             BigDecimal amount,
                             LocalDateTime dateCreated,
                             LocalDateTime dateUpdated,
                             String status){

    public static OrderResponse map(Order order, Provider provider, Product product ) {
        return new OrderResponse(order.id().value(),
                ProviderResponse.map(provider),
                ProductResponse.map(product),
                order.quantity().value(),
                order.amount().value(),
                order.dateCreated(),
                order.dateUpdated(),
                order.status().name());
    }
}
