package ar.edu.ungs.fleet_manager.products.application.create;

import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import ar.edu.ungs.fleet_manager.orders.domain.Quantity;
import ar.edu.ungs.fleet_manager.products.application.ProductRequest;
import ar.edu.ungs.fleet_manager.products.application.find.ProductByNameBrandFinder;
import ar.edu.ungs.fleet_manager.products.application.search.ProductsAllSearcher;
import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.products.domain.ProductRepository;
import ar.edu.ungs.fleet_manager.products.domain.services.ProductFinder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public final class ProductCreator {
    private final ProductByNameBrandFinder finder;
    private final ProductRepository repository;

    public ProductCreator(ProductByNameBrandFinder finder, ProductRepository repository) {
        this.finder = finder;
        this.repository = repository;
    }

    public void execute(ProductRequest request) {

        Optional<Product> ifProductExists = this.finder.execute(request.name(), request.brand());

        Product product;

        if (ifProductExists.isPresent()) {

            product = Product.build(ifProductExists.get().id().value(),
                    ifProductExists.get().name().value(),
                    ifProductExists.get().brand().value(),
                    ifProductExists.get().description().value(),
                    ifProductExists.get().category().value(),
                    request.quantity() + ifProductExists.get().quantity().value(),
                    ifProductExists.get().measurement().name(),
                    ifProductExists.get().price().value(),
                    ifProductExists.get().preferenceProviderId().value(),
                    ifProductExists.get().minStock().value(),
                    ifProductExists.get().automaticPurchase().name(),
                    ifProductExists.get().enterpriseId().value());
        }else {
            product = Product.create(request.name(),
                    request.brand(),
                    request.description(),
                    request.category(),
                    request.quantity(),
                    request.measurement(),
                    request.price(),
                    request.providerId(),
                    request.minStock(),
                    request.enterpriseId());

        }



        this.repository.save(product);
    }
}
