package ar.edu.ungs.fleet_manager.products.application.find;

import ar.edu.ungs.fleet_manager.products.application.ProductResponse;
import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.products.domain.services.ProductFinder;
import org.springframework.stereotype.Component;

@Component
public class ProductByIdFinder {
    private final ProductFinder productFinder;

    public ProductByIdFinder(ProductFinder productFinder) {
        this.productFinder = productFinder;
    }

    public ProductResponse execute(String id) {
        Product product = this.productFinder.execute(new ProductId(id));

        return ProductResponse.map(product);
    }
}
