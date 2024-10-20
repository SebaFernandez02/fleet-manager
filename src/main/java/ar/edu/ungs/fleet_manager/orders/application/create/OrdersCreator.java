package ar.edu.ungs.fleet_manager.orders.application.create;

import ar.edu.ungs.fleet_manager.orders.application.AddOrderProductRequest;
import ar.edu.ungs.fleet_manager.orders.application.OrderRequest;
import ar.edu.ungs.fleet_manager.orders.application.update.OrderProductAdder;
import ar.edu.ungs.fleet_manager.orders.domain.Order;
import ar.edu.ungs.fleet_manager.orders.domain.services.OrderFinder;
import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.products.domain.ProductRepository;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderId;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class OrdersCreator {
    private final ProductRepository productRepository;
    private final OrderFinder orderFinder;
    private final OrderProductAdder productAdder;
    private final OrderCreator orderCreator;

    public OrdersCreator(ProductRepository productRepository,
                         OrderFinder orderFinder,
                         OrderProductAdder productAdder,
                         OrderCreator orderCreator) {
        this.productRepository = productRepository;
        this.orderFinder = orderFinder;
        this.productAdder = productAdder;
        this.orderCreator = orderCreator;
    }

    public void execute() {
        List<Product> products =  this.productRepository.searchAllNoStockAutoPurchase();

        products.forEach(this::createOrder);
    }

    private void createOrder(Product x) {
        if (x.preferenceProviderId().isEmpty()) {
            return;
        }

        ProviderId providerId = x.preferenceProviderId().get();

        try {
            Order order = this.orderFinder.execute(providerId);

            var request = new AddOrderProductRequest(x.id().value(), x.minStock().value(), x.price().value().multiply(BigDecimal.valueOf(x.minStock().value())));

            productAdder.execute(order.id().value(), request);
        } catch (NotFoundException ignored) {
            OrderRequest request = new OrderRequest(providerId.value());

            this.orderCreator.execute(request);

            // En la siguiente corrida del job va a encontrar la orden activa y va a añadir el detalle
        }
    }

}

