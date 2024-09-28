package ar.edu.ungs.fleet_manager.products.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.products.application.ProductResponse;
import ar.edu.ungs.fleet_manager.products.application.search.ProductsAllSearcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetProductsRestController {
    private final ProductsAllSearcher searcher;

    public GetProductsRestController(ProductsAllSearcher searcher) {
        this.searcher = searcher;
    }

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductResponse>> handle(){
        var result = this.searcher.execute();

        return ResponseEntity.ok(result);
    }
}
