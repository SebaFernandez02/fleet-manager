package ar.edu.ungs.fleet_manager.orders.domain.services;

import ar.edu.ungs.fleet_manager.orders.domain.Order;
import ar.edu.ungs.fleet_manager.orders.domain.OrderId;
import ar.edu.ungs.fleet_manager.orders.domain.OrderRepository;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderId;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class OrderFinder {
    private final OrderRepository repository;

    public OrderFinder(OrderRepository repository) {
        this.repository = repository;
    }

    public Order execute(OrderId id) {
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException(String.format("order %s not found", id.value())));
    }

    public Order execute(ProviderId id){
        return this.repository.findByProviderId(id).orElseThrow(() -> new NotFoundException(String.format("order %s not found", id.value())));
    }
}
