package ar.edu.ungs.fleet_manager.orders.application;

import ar.edu.ungs.fleet_manager.orders.domain.Order;
import ar.edu.ungs.fleet_manager.products.application.ProductResponse;
import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.providers.application.ProviderResponse;
import ar.edu.ungs.fleet_manager.providers.domain.Provider;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse (String id,
                             ProviderResponse provider,
                             List<ProductResponse> productResponses,
                             BigDecimal amount,
                             LocalDateTime dateCreated,
                             LocalDateTime dateUpdated,
                             String status){

    public static OrderResponse map(Order order, Provider provider, List<Product> products ) {
        return new OrderResponse(order.id().value(),
                ProviderResponse.map(provider),
                ProductResponse.map(products),
                order.amount().value(),
                order.dateCreated(),
                order.dateUpdated(),
                order.status().name());
    }
}
