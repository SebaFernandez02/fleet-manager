package ar.edu.ungs.fleet_manager.orders.application.find;

import ar.edu.ungs.fleet_manager.orders.application.OrderResponse;
import ar.edu.ungs.fleet_manager.orders.domain.Order;
import ar.edu.ungs.fleet_manager.orders.domain.OrderId;
import ar.edu.ungs.fleet_manager.orders.domain.services.OrderFinder;
import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.products.domain.services.ProductFinder;
import ar.edu.ungs.fleet_manager.providers.domain.Provider;
import ar.edu.ungs.fleet_manager.providers.domain.services.ProviderFinder;
import org.springframework.stereotype.Component;

import java.util.List;



@Component
public final class OrderByIdFinder {
    private final OrderFinder orderFinder;
    private final ProductFinder productFinder;
    private final ProviderFinder providerFinder;

    public OrderByIdFinder(OrderFinder orderFinder, ProductFinder productFinder, ProviderFinder providerFinder) {
        this.orderFinder = orderFinder;
        this.productFinder = productFinder;
        this.providerFinder = providerFinder;
    }

    public OrderResponse execute(String orderId){
        Order order = this.orderFinder.execute(new OrderId(orderId));



      //  Product product = this.productFinder.execute(order.productId());

        List<Product> products = order.products().keySet().stream()
                .map(this.productFinder::execute)
                .toList();

        Provider provider = this.providerFinder.execute(order.providerId());

        return OrderResponse.map(order, provider, products);
    }
}
