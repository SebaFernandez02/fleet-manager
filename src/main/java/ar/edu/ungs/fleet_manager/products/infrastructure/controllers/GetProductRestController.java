package ar.edu.ungs.fleet_manager.products.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.products.application.ProductResponse;
import ar.edu.ungs.fleet_manager.products.application.find.ProductByIdFinder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetProductRestController {
    private final ProductByIdFinder finder;

    public GetProductRestController(ProductByIdFinder finder) {
        this.finder = finder;
    }

    @GetMapping("/api/products/{id}")
    public ResponseEntity<ProductResponse> handle(@PathVariable String id){
        var result = this.finder.execute(id);

        return ResponseEntity.ok(result);
    }
}
