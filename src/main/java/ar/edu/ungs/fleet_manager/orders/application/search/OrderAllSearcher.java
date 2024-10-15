package ar.edu.ungs.fleet_manager.orders.application.search;


import ar.edu.ungs.fleet_manager.orders.application.OrderResponse;
import ar.edu.ungs.fleet_manager.orders.domain.Order;
import ar.edu.ungs.fleet_manager.orders.domain.OrderRepository;
import ar.edu.ungs.fleet_manager.products.application.find.ProductByIdFinder;
import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.products.domain.services.ProductFinder;
import ar.edu.ungs.fleet_manager.providers.domain.Provider;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderId;
import ar.edu.ungs.fleet_manager.providers.domain.services.ProviderFinder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public final class OrderAllSearcher {
    private final OrderRepository repository;
    private final ProviderFinder providerFinder;
    private final ProductByIdFinder productFinder;

    public OrderAllSearcher(OrderRepository repository, ProviderFinder providerFinder, ProductByIdFinder productFinder) {
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
        Provider provider = this.providerFinder.execute(new ProviderId(order.providerId().value()));
        List<Product> products = Collections.emptyList();

        return OrderResponse.map(order, provider, products);
    }
}
