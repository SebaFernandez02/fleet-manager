package ar.edu.ungs.fleet_manager.orders.application.update;

import ar.edu.ungs.fleet_manager.orders.domain.Order;
import ar.edu.ungs.fleet_manager.orders.domain.OrderId;
import ar.edu.ungs.fleet_manager.orders.domain.OrderRepository;
import ar.edu.ungs.fleet_manager.orders.domain.OrderStatus;
import ar.edu.ungs.fleet_manager.orders.domain.services.OrderFinder;
import ar.edu.ungs.fleet_manager.products.domain.services.ProductQuantityAdder;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusUpdater {
    private final OrderFinder orderFinder;
    private final OrderRepository repository;
    private final ProductQuantityAdder productQuantityAdder;

    public OrderStatusUpdater(OrderFinder orderFinder,
                              OrderRepository repository,
                              ProductQuantityAdder productQuantityAdder){
        this.orderFinder = orderFinder;
        this.repository = repository;
        this.productQuantityAdder = productQuantityAdder;
    }

    public void execute(String id, String status) {
        OrderStatus statusToUpdate = OrderStatus.parse(status);

        Order order = this.orderFinder.execute(new OrderId(id));

        order.setStatus(statusToUpdate);

        this.repository.save(order);

        if (order.isCompleted()) {
            order.items().forEach(x -> this.productQuantityAdder.execute(x.productId(), x.quantity()));
        }
    }
}
