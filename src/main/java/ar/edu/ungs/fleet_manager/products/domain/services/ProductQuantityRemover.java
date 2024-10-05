package ar.edu.ungs.fleet_manager.products.domain.services;

import ar.edu.ungs.fleet_manager.orders.domain.Quantity;
import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.products.domain.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductQuantityRemover {
    private final ProductRepository repository;
    private final ProductFinder productFinder;

    public ProductQuantityRemover(ProductRepository repository, ProductFinder productFinder) {
        this.repository = repository;
        this.productFinder = productFinder;
    }

    public void execute(ProductId id, Quantity quantity){
        Product product = this.productFinder.execute(id);

        product.substract(quantity);

        this.repository.save(product);
    }
}
