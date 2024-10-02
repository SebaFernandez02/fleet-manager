package ar.edu.ungs.fleet_manager.orders.application.update;

import ar.edu.ungs.fleet_manager.orders.application.OrderStatusRequest;
import ar.edu.ungs.fleet_manager.orders.domain.OrderId;
import ar.edu.ungs.fleet_manager.orders.domain.OrderRepository;
import ar.edu.ungs.fleet_manager.orders.domain.services.OrderFinder;
import org.springframework.stereotype.Component;



@Component
public class OrderUpdater {

    private final OrderFinder orderFinder;
    private final OrderRepository repository;

    public OrderUpdater(OrderFinder orderFinder, OrderRepository repository){
        this.orderFinder = orderFinder;
        this.repository = repository;
    }

    public void execute(String id, OrderStatusRequest request)  {

        var order = this.orderFinder.execute(new OrderId(id));

        order.setStatus(request.status());

        this.repository.save(order);
    }


}
