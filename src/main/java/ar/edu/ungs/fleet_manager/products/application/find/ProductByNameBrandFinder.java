package ar.edu.ungs.fleet_manager.products.application.find;

import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.products.domain.ProductBrand;
import ar.edu.ungs.fleet_manager.products.domain.ProductName;
import ar.edu.ungs.fleet_manager.products.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductByNameBrandFinder {
    private final ProductRepository repository;

    public ProductByNameBrandFinder(ProductRepository repository) {
        this.repository = repository;
    }

    public Optional<Product> execute(String name, String brand){
        return this.repository.searchByNameBrand(new ProductName(name), new ProductBrand(brand));
    }
}
