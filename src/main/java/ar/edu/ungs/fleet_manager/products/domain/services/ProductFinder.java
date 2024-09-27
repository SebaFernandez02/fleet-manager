package ar.edu.ungs.fleet_manager.products.domain.services;

import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.products.domain.ProductRepository;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public final class ProductFinder {

    public final ProductRepository productRepository;

    public ProductFinder(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    public Product execute(ProductId id){

        return this.productRepository.findById(id)
                                    .orElseThrow(() -> new NotFoundException(String.format("product %s not found", id.value())));
    }
}