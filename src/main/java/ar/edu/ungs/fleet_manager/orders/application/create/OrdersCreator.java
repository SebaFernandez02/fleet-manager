package ar.edu.ungs.fleet_manager.orders.application.create;

import ar.edu.ungs.fleet_manager.orders.application.OrderRequest;
import ar.edu.ungs.fleet_manager.orders.domain.OrderTemplate;
import ar.edu.ungs.fleet_manager.orders.domain.services.OrderFinder;
import ar.edu.ungs.fleet_manager.orders.domain.services.OrderTemplateFinder;
import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.products.domain.services.ProductsWithoutStockSearcher;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrdersCreator {
    private final OrderCreator creator;
    private final ProductsWithoutStockSearcher productSearcher;
    private final OrderTemplateFinder finder;
    private final OrderFinder orderFinder;

    public OrdersCreator(OrderCreator creator, ProductsWithoutStockSearcher productSearcher, OrderTemplateFinder finder, OrderFinder orderFinder) {
        this.creator = creator;
        this.productSearcher = productSearcher;
        this.finder = finder;
        this.orderFinder = orderFinder;
    }

    public void execute() {
        List<Product> products = this.productSearcher.execute();
        for (Product product : products) {
            try {
                orderFinder.execute(product.id());

            }catch (NotFoundException ignored){
                findByProduct(product.id()).ifPresent(template -> {
                    OrderRequest order = new OrderRequest(template.provider(),
                            template.product(),
                            template.quantity(),
                            template.amount());
                    this.creator.execute(order);
                });
            }

        }
    }

    private Optional<OrderTemplate> findByProduct(ProductId id){
        try {
            return Optional.of(this.finder.execute(id.value()));
        }catch (NotFoundException ignore){
            return Optional.empty();
        }
    }
}

