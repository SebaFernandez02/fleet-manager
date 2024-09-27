package ar.edu.ungs.fleet_manager.orders.application.search;


import ar.edu.ungs.fleet_manager.orders.application.OrderResponse;
import ar.edu.ungs.fleet_manager.orders.domain.Order;
import ar.edu.ungs.fleet_manager.orders.domain.OrderRepository;
import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.products.domain.services.ProductFinder;
import ar.edu.ungs.fleet_manager.providers.domain.Provider;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderCuit;
import ar.edu.ungs.fleet_manager.providers.domain.services.ProviderFinder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class OrderAllSearcher {
    private final OrderRepository repository;
    private final ProviderFinder providerFinder;
    private final ProductFinder productFinder;

    public OrderAllSearcher(OrderRepository repository, ProviderFinder providerFinder, ProductFinder productFinder) {
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
        Provider provider = this.providerFinder.execute(new ProviderCuit(order.provider().value()));
        Product product = this.productFinder.execute(new ProductId(order.product().value()));

        return OrderResponse.map(order, provider, product);
    }
}
