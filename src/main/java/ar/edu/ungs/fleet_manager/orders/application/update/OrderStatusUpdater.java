package ar.edu.ungs.fleet_manager.orders.application.update;

import ar.edu.ungs.fleet_manager.configs.application.ConfigResponse;
import ar.edu.ungs.fleet_manager.configs.application.find.ConfigByTypeFinder;
import ar.edu.ungs.fleet_manager.configs.domain.Config;
import ar.edu.ungs.fleet_manager.configs.domain.ConfigType;
import ar.edu.ungs.fleet_manager.orders.domain.Order;
import ar.edu.ungs.fleet_manager.orders.domain.OrderId;
import ar.edu.ungs.fleet_manager.orders.domain.OrderRepository;
import ar.edu.ungs.fleet_manager.orders.domain.OrderStatus;
import ar.edu.ungs.fleet_manager.orders.domain.services.OrderFinder;
import ar.edu.ungs.fleet_manager.products.domain.services.ProductQuantityAdder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderStatusUpdater {
    private final OrderFinder orderFinder;
    private final OrderRepository repository;
    private final ProductQuantityAdder productQuantityAdder;
    private final ConfigByTypeFinder configByTypeFinder;


    public OrderStatusUpdater(OrderFinder orderFinder,
                              OrderRepository repository,
                              ProductQuantityAdder productQuantityAdder, ConfigByTypeFinder configByTypeFinder){
        this.orderFinder = orderFinder;
        this.repository = repository;
        this.productQuantityAdder = productQuantityAdder;
        this.configByTypeFinder = configByTypeFinder;
    }

    public void execute(String id, String status) {


        OrderStatus statusToUpdate = OrderStatus.parse(status);

        Order order = this.orderFinder.execute(new OrderId(id));

        ConfigResponse threshold = this.configByTypeFinder.execute(ConfigType.OC_THRESHOLD.name(), order.enterpriseId());

        statusToUpdate = statusToUpdate.equals(OrderStatus.COMPLETED) &&
                         order.amount().value().compareTo(BigDecimal.valueOf(Integer.parseInt(threshold.value()))) > 0 &&
                         !order.status().equals(OrderStatus.APPROVED)
                         ? OrderStatus.REVIEW
                         : statusToUpdate;

        order.setStatus(statusToUpdate);

        this.repository.save(order);

        if (order.isCompleted()) {
            order.items().forEach(x -> this.productQuantityAdder.execute(x.productId(), x.quantity()));
        }
    }
}
