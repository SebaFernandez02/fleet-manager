package ar.edu.ungs.fleet_manager.orders.application.autoCreate;

import ar.edu.ungs.fleet_manager.orders.application.OrderRequest;
import ar.edu.ungs.fleet_manager.orders.application.autoFind.OrderTemplateByProductFinder;
import ar.edu.ungs.fleet_manager.orders.application.create.OrderCreator;
import ar.edu.ungs.fleet_manager.orders.domain.Order;
import ar.edu.ungs.fleet_manager.orders.domain.OrderTemplate;
import ar.edu.ungs.fleet_manager.orders.infrastructure.persistence.PostgresOrderRepository;
import ar.edu.ungs.fleet_manager.orders.infrastructure.persistence.PostgresOrderTemplateRepository;
import ar.edu.ungs.fleet_manager.products.application.ProductResponse;
import ar.edu.ungs.fleet_manager.products.application.search.ProductsNoStockSearcher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderNoStockCreator {
    private final OrderCreator creator;
    private final ProductsNoStockSearcher productSearcher;
    private final PostgresOrderTemplateRepository repository;
    private final PostgresOrderRepository orderRepository;
    private final OrderTemplateByProductFinder finder;

    public OrderNoStockCreator(OrderCreator creator, ProductsNoStockSearcher productSearcher, PostgresOrderTemplateRepository repository, PostgresOrderRepository orderRepository, OrderTemplateByProductFinder finder) {
        this.creator = creator;
        this.productSearcher = productSearcher;
        this.repository = repository;
        this.orderRepository = orderRepository;
        this.finder = finder;
    }


    public void execute() {
        List<ProductResponse> products = this.productSearcher.execute();
        for (ProductResponse product : products) {
            OrderTemplate template = finder.findByProduct(product.id());
            Order order = Order.create(template.provider(),
                                       template.product(),
                                       template.quantity(),
                                       template.amount());

            this.orderRepository.save(order);
        }
    }
}

