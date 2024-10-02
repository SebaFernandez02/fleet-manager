package ar.edu.ungs.fleet_manager.orders.domain.services;

import ar.edu.ungs.fleet_manager.orders.domain.*;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class OrderTemplateFinder {
    private final OrderTemplateRepository repository;

    public OrderTemplateFinder(OrderTemplateRepository repository) {
        this.repository = repository;
    }

    public OrderTemplate execute(ProductId id) {
        return this.repository.findByProduct(id)
                              .orElseThrow(() -> new NotFoundException(String.format("order %s not found", id)));
    }
}
