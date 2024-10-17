package ar.edu.ungs.fleet_manager.orders.application.find;

import ar.edu.ungs.fleet_manager.orders.application.OrderProductResponse;
import ar.edu.ungs.fleet_manager.orders.application.OrderResponse;
import ar.edu.ungs.fleet_manager.orders.domain.Order;
import ar.edu.ungs.fleet_manager.orders.domain.OrderId;
import ar.edu.ungs.fleet_manager.orders.domain.services.OrderFinder;
import ar.edu.ungs.fleet_manager.products.application.find.ProductByIdFinder;
import ar.edu.ungs.fleet_manager.providers.application.ProviderResponse;
import ar.edu.ungs.fleet_manager.providers.application.find.ProviderByIdFinder;
import org.springframework.stereotype.Component;

import java.util.List;



@Component
public final class OrderByIdFinder {
    private final OrderFinder orderFinder;
    private final ProductByIdFinder productFinder;
    private final ProviderByIdFinder providerFinder;

    public OrderByIdFinder(OrderFinder orderFinder, ProductByIdFinder productFinder, ProviderByIdFinder providerFinder) {
        this.orderFinder = orderFinder;
        this.productFinder = productFinder;
        this.providerFinder = providerFinder;
    }

    public OrderResponse execute(String orderId){
        Order order = this.orderFinder.execute(new OrderId(orderId));
        ProviderResponse provider = this.providerFinder.execute(order.providerId().value());
        List<OrderProductResponse> products = order.items().stream().map(x -> new OrderProductResponse(this.productFinder.execute(x.productId().value()), x.quantity().value())).toList();
        return OrderResponse.map(order, provider, products);
    }
}
