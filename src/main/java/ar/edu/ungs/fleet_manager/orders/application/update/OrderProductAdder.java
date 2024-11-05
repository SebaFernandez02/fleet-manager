package ar.edu.ungs.fleet_manager.orders.application.update;

import ar.edu.ungs.fleet_manager.orders.application.AddOrderProductRequest;
import ar.edu.ungs.fleet_manager.orders.domain.*;
import ar.edu.ungs.fleet_manager.orders.domain.services.OrderFinder;
import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.products.domain.ProductRepository;
import ar.edu.ungs.fleet_manager.products.domain.services.ProductFinder;
import ar.edu.ungs.fleet_manager.providers.application.ProviderResponse;
import ar.edu.ungs.fleet_manager.providers.application.search.ProviderByProductSearcher;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public final class OrderProductAdder {
    private final OrderRepository repository;
    private final ProductFinder productFinder;
    private final OrderFinder orderFinder;
    private final ProviderByProductSearcher searcher;

    public OrderProductAdder(OrderRepository repository, ProductFinder productFinder, OrderFinder orderFinder, ProviderByProductSearcher searcher) {
        this.repository = repository;
        this.productFinder = productFinder;
        this.orderFinder = orderFinder;
        this.searcher = searcher;
    }

    public void execute(String id, AddOrderProductRequest request) {
        Order order = this.orderFinder.execute(new OrderId(id));

        Product product = this.productFinder.execute(new ProductId(request.productId()));

        this.searcher.execute(product.id()).stream().map(ProviderResponse::id).filter(providerId -> providerId.equals(order.providerId().value())).findAny().orElseThrow(() -> new InvalidParameterException(String.format(
                "The product with ID '%s' is not supplied by the provider associated with the order.",
                request.productId())));

        var orderContainsProduct = order.items()
                                        .stream()
                                        .filter(x -> x.productId().value().equals(product.id().value()))
                                        .findAny();

        if (orderContainsProduct.isEmpty()) {
            Quantity quantity = new Quantity(request.quantity());

            BigDecimal amount = product.price().value().multiply(BigDecimal.valueOf(quantity.value()));

            order.add(product.id(), quantity, amount);

            this.repository.save(order);
        }else{
            OrderProduct oldProduct = orderContainsProduct.get();

            Quantity quantity = new Quantity(oldProduct.quantity().value() + request.quantity());

            BigDecimal amount = product.price().value().multiply(BigDecimal.valueOf(quantity.value()));

            OrderProduct newProduct = new OrderProduct(oldProduct.productId(), quantity , amount);

            BigDecimal addOrderAmount = product.price().value().multiply(BigDecimal.valueOf(request.quantity()));

            order.addExistingproduct(newProduct, addOrderAmount);

            this.repository.save(order);
        }
    }
}
