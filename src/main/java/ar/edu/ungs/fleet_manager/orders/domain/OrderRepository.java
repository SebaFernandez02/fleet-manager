package ar.edu.ungs.fleet_manager.orders.domain;

import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.providers.domain.Provider;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderCuit;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderId;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    void save(Order order);

    Optional<Order> findById(OrderId id);

    List<Order> searchAll();

    Optional<Order> findByProduct(ProductId product);
}
