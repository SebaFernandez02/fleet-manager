package ar.edu.ungs.fleet_manager.products.application.update;

import ar.edu.ungs.fleet_manager.products.application.ProductRequest;
import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.products.domain.ProductAutomaticPurchase;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.products.domain.ProductRepository;
import ar.edu.ungs.fleet_manager.products.domain.services.ProductFinder;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class ProductUpdater {
    private final ProductRepository repository;
    private final ProductFinder productFinder;

    public ProductUpdater(ProductRepository repository, ProductFinder productFinder) {
        this.repository = repository;
        this.productFinder = productFinder;
    }

    public void execute(String id, ProductRequest request) {
        Product product = this.productFinder.execute(new ProductId(id));

        Optional.ofNullable(request.name()).ifPresent(product::updateName);
        Optional.ofNullable(request.category()).ifPresent(product::updateCategory);
        Optional.ofNullable(request.brand()).ifPresent(product::updateBrand);
        Optional.ofNullable(request.description()).ifPresent(product::updateDescription);
        Optional.ofNullable(request.measurement()).ifPresent(product::updateMeasurement);
        Optional.ofNullable(request.price()).ifPresent(product::updatePrice);
        Optional.ofNullable(request.providerId()).ifPresent(product::updatePrefProvider);
        Optional.ofNullable(request.minStock()).ifPresent(product::setMinStock);

        this.repository.save(product);
    }

    public void execute(String id, String autoPurchase){
        Product product = this.productFinder.execute(new ProductId(id));

        ProductAutomaticPurchase status = ProductAutomaticPurchase.parse(autoPurchase);

        switch (status) {
            case ENABLED -> product.enableAutoPurchase();
            case DISABLED -> product.disableAutoPurchase();
        }

        this.repository.save(product);
    }

}
