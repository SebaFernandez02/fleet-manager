package ar.edu.ungs.fleet_manager.orders.application.update;

import ar.edu.ungs.fleet_manager.orders.application.OrderStatusRequest;
import ar.edu.ungs.fleet_manager.orders.domain.Order;
import ar.edu.ungs.fleet_manager.orders.domain.OrderId;
import ar.edu.ungs.fleet_manager.orders.domain.OrderRepository;
import ar.edu.ungs.fleet_manager.orders.domain.OrderStatus;
import ar.edu.ungs.fleet_manager.orders.domain.services.OrderFinder;
import ar.edu.ungs.fleet_manager.products.application.update.ProductUpdater;
import ar.edu.ungs.fleet_manager.products.domain.ProductQuantity;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;
import org.springframework.stereotype.Component;



@Component
public class OrderUpdater {

    private final OrderFinder orderFinder;
    private final OrderRepository repository;
    private final ProductUpdater productUpdater;

    public OrderUpdater(OrderFinder orderFinder, OrderRepository repository,ProductUpdater productUpdater){
        this.orderFinder = orderFinder;
        this.repository = repository;
        this.productUpdater = productUpdater;
    }

    public void execute(String id, OrderStatusRequest request)  {

        Order order = this.orderFinder.execute(new OrderId(id));

        order.setStatus(request.status());

        if(OrderStatus.parse(request.status()).equals(OrderStatus.COMPLETED)){

            var quantity = order.quantity().value();
            var productId = order.productId().value();
            this.productUpdater.updateQuantity(productId,quantity);

        }

        this.repository.save(order);


    }


}
