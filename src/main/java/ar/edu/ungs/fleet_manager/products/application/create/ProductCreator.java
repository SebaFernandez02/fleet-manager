package ar.edu.ungs.fleet_manager.products.application.create;

import ar.edu.ungs.fleet_manager.products.application.ProductRequest;
import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.products.domain.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public final class ProductCreator {

    private final ProductRepository repository;

    public ProductCreator(ProductRepository repository) {
        this.repository = repository;
    }

    public void execute(ProductRequest request){
        Product product = Product.create(request.name(),
                                         request.brand(),
                                         request.description(),
                                         request.category());

        this.repository.save(product);
    }
}
