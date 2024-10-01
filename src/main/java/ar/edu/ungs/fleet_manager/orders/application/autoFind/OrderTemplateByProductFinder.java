package ar.edu.ungs.fleet_manager.orders.application.autoFind;

import ar.edu.ungs.fleet_manager.orders.domain.OrderTemplate;
import ar.edu.ungs.fleet_manager.orders.domain.services.OrderTemplateFinder;
import org.springframework.stereotype.Component;

@Component
public class OrderTemplateByProductFinder {
    private final OrderTemplateFinder finder;

    public OrderTemplateByProductFinder(OrderTemplateFinder finder) {
        this.finder = finder;
    }


    public OrderTemplate findByProduct(String id) {
        return this.finder.execute(id);
    }
}
