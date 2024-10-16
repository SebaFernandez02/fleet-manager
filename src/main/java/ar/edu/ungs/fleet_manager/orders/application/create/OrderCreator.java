package ar.edu.ungs.fleet_manager.orders.application.create;

import ar.edu.ungs.fleet_manager.orders.application.OrderRequest;
import ar.edu.ungs.fleet_manager.orders.domain.Order;
import ar.edu.ungs.fleet_manager.orders.domain.OrderRepository;

import org.springframework.stereotype.Component;

@Component
public final class OrderCreator {
    private final OrderRepository repository;

    public OrderCreator(OrderRepository repository) {
        this.repository = repository;
    }

    public void execute(OrderRequest request) {
        Order order = Order.create(request.providerId());

        this.repository.save(order);
    }
}

