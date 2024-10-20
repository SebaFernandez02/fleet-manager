package ar.edu.ungs.fleet_manager.orders.domain;

import ar.edu.ungs.fleet_manager.products.domain.ProductId;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderTemplateRepository {

    void save(OrderTemplate order);

    Optional<OrderTemplate> findByProduct(ProductId product);
}
