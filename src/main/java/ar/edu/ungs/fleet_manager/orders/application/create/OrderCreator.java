package ar.edu.ungs.fleet_manager.orders.application.create;

import ar.edu.ungs.fleet_manager.orders.application.OrderRequest;
import ar.edu.ungs.fleet_manager.orders.domain.Order;
import ar.edu.ungs.fleet_manager.orders.domain.OrderProduct;
import ar.edu.ungs.fleet_manager.orders.domain.OrderRepository;
import ar.edu.ungs.fleet_manager.orders.domain.services.OrderFinder;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;


@Component
public final class OrderCreator {
    private final OrderRepository repository;
    private final OrderFinder finder;

    public OrderCreator(OrderRepository repository,
                        OrderFinder finder) {
        this.repository = repository;
        this.finder = finder;
    }

    public void execute (OrderRequest request){
        this.ensureThisOrderNotExists(request.productId());

        Order order = Order.create(request.providerId(),
                                   request.productId(),
                                   request.quantity(),
                                   request.amount());

        this.repository.save(order);
    }

    private void ensureThisOrderNotExists(String product) {
        try {
            this.finder.execute(new OrderProduct(product));

            throw new InvalidParameterException(String.format("the order %s already exists", product));
        } catch (NotFoundException ignored) {}
    }
}

