package ar.edu.ungs.fleet_manager.orders.domain;

import ar.edu.ungs.fleet_manager.providers.domain.ProviderId;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    void save(Order order);

    Optional<Order> findById(OrderId id);

    List<Order> searchAll();

    Optional<Order> findByProviderId(ProviderId id);
}
