package ar.edu.ungs.fleet_manager.orders.application.search;

import ar.edu.ungs.fleet_manager.orders.application.OrderProductResponse;
import ar.edu.ungs.fleet_manager.orders.application.OrderResponse;
import ar.edu.ungs.fleet_manager.orders.domain.Order;
import ar.edu.ungs.fleet_manager.orders.domain.OrderRepository;
import ar.edu.ungs.fleet_manager.products.application.find.ProductByIdFinder;

import ar.edu.ungs.fleet_manager.providers.application.ProviderResponse;
import ar.edu.ungs.fleet_manager.providers.application.find.ProviderByIdFinder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class OrderAllSearcher {
    private final OrderRepository repository;
    private final ProviderByIdFinder providerFinder;
    private final ProductByIdFinder productFinder;

    public OrderAllSearcher(OrderRepository repository, ProviderByIdFinder providerFinder, ProductByIdFinder productFinder) {
        this.repository = repository;
        this.providerFinder = providerFinder;
        this.productFinder = productFinder;
    }

    public List<OrderResponse> execute() {
        return this.repository.searchAll()
                              .stream()
                              .map(this::apply)
                              .toList();
    }

    private OrderResponse apply(Order order) {
        ProviderResponse provider = this.providerFinder.execute(order.providerId().value());
        List<OrderProductResponse> products = order.items().stream().map(x -> new OrderProductResponse(this.productFinder.execute(x.productId().value()), x.quantity().value(), x.amount())).toList();
        return OrderResponse.map(order, provider, products);
    }
}
