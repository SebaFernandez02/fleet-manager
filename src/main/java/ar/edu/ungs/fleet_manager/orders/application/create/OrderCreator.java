package ar.edu.ungs.fleet_manager.orders.application.create;

import ar.edu.ungs.fleet_manager.orders.application.OrderRequest;
import ar.edu.ungs.fleet_manager.orders.domain.Order;
import ar.edu.ungs.fleet_manager.orders.domain.OrderRepository;

import ar.edu.ungs.fleet_manager.orders.domain.services.OrderFinder;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderId;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public final class OrderCreator {
    private final OrderRepository repository;
    private final OrderFinder finder;

    public OrderCreator(OrderRepository repository, OrderFinder finder) {
        this.repository = repository;
        this.finder = finder;
    }

    public void execute(OrderRequest request) {
        ensureProviderNotContainsActiveOrders(request);

        Order order = Order.create(request.providerId());

        this.repository.save(order);
    }

    private void ensureProviderNotContainsActiveOrders(OrderRequest request) {
        ProviderId id = new ProviderId(request.providerId());

        try {
            this.finder.execute(id);

            throw new InvalidParameterException("order with provider already exists");
        } catch (NotFoundException ignored) {

        }
    }
}

