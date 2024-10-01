package ar.edu.ungs.fleet_manager.orders.application.create;

import ar.edu.ungs.fleet_manager.orders.application.OrderRequest;
import ar.edu.ungs.fleet_manager.orders.domain.Order;
import ar.edu.ungs.fleet_manager.orders.domain.OrderRepository;
import ar.edu.ungs.fleet_manager.orders.domain.OrderTemplate;
import ar.edu.ungs.fleet_manager.orders.domain.OrderTemplateRepository;
import org.springframework.stereotype.Component;


@Component
public final class OrderCreator {
    private final OrderRepository repository;
    private final OrderTemplateRepository templateRepository;

    public OrderCreator(OrderRepository repository, OrderTemplateRepository templateRepository) {
        this.repository = repository;
        this.templateRepository = templateRepository;
    }

    public void execute (OrderRequest request){
        Order order = Order.create(request.providerId(),
                                   request.productId(),
                                   request.quantity(),
                                   request.amount());

        OrderTemplate template = new OrderTemplate(request.providerId(), request.productId(), request.quantity(), request.amount());
        this.templateRepository.save(template);

        this.repository.save(order);
    }
}

