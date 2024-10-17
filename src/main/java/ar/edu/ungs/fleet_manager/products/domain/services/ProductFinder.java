package ar.edu.ungs.fleet_manager.products.domain.services;

import ar.edu.ungs.fleet_manager.orders.domain.OrderProduct;
import ar.edu.ungs.fleet_manager.orders.domain.Quantity;
import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.products.domain.ProductRepository;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
public final class ProductFinder {

    public final ProductRepository productRepository;

    public ProductFinder(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    public Product execute(ProductId id){
        return this.productRepository.findById(id)
                                    .orElseThrow(() -> new NotFoundException(String.format("productId %s not found", id.value())));
    }

    public List<Product> searchList(List<OrderProduct> products){

        List<Product> productList = new ArrayList<>();

        for(OrderProduct product: products){
            String id= product.productId().value();
            productList.add(execute(product.productId()));
        }
            return productList;
    }

}
