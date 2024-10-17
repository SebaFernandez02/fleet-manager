package ar.edu.ungs.fleet_manager.orders.application;

import ar.edu.ungs.fleet_manager.orders.domain.Order;
import ar.edu.ungs.fleet_manager.providers.application.ProviderResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(String id,
                            ProviderResponse provider,
                            List<OrderProductResponse> items,
                            BigDecimal amount,
                            LocalDateTime dateCreated,
                            LocalDateTime dateUpdated,
                            String status){
    public static OrderResponse map(Order order, ProviderResponse provider, List<OrderProductResponse> products) {
        return new OrderResponse(order.id().value(),
                provider,
                products,
                order.amount().value(),
                order.dateCreated(),
                order.dateUpdated(),
                order.status().name());
    }
}
