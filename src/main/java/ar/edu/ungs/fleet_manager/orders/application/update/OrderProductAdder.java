package ar.edu.ungs.fleet_manager.orders.application.update;

import ar.edu.ungs.fleet_manager.orders.application.AddOrderProductRequest;
import ar.edu.ungs.fleet_manager.orders.domain.*;
import ar.edu.ungs.fleet_manager.orders.domain.services.OrderFinder;
import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.products.domain.services.ProductFinder;
import org.springframework.stereotype.Component;

@Component
public final class OrderProductAdder {
    private final OrderRepository repository;
    private final ProductFinder productFinder;
    private final OrderFinder orderFinder;

    public OrderProductAdder(OrderRepository repository, ProductFinder productFinder, OrderFinder orderFinder) {
        this.repository = repository;
        this.productFinder = productFinder;
        this.orderFinder = orderFinder;
    }

    public void execute(String id, AddOrderProductRequest request) {
        Order order = this.orderFinder.execute(new OrderId(id));

        Product product = this.productFinder.execute(new ProductId(request.productId()));

        Quantity quantity = new Quantity(request.quantity());

        order.add(product.id(), quantity, request.amount());

        this.repository.save(order);
    }
}
