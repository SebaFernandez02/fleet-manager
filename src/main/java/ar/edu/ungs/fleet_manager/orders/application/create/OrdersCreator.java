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

        products.forEach(product -> {
            if (!this.containsPendingOrder(product)) {
                this.findByProduct(product).ifPresent(this.creator::execute);
            }
        });
    }

    private Boolean containsPendingOrder(Product product) {
        try {
            orderFinder.execute(product.id());
            return true;
        } catch (NotFoundException ignored) {
            return false;
        }
    }

    private Optional<OrderTemplate> findByProduct(Product product){
        try {
            return Optional.of(this.finder.execute(product.id()));
        }catch (NotFoundException ignore){
            return Optional.empty();
        }
    }
}

