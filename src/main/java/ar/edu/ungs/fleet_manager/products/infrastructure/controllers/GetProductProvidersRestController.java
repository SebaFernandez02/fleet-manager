package ar.edu.ungs.fleet_manager.products.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.products.application.ProductProvidersResponse;
import ar.edu.ungs.fleet_manager.products.application.find.ProductByIdFinder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetProductProvidersRestController {
    private final ProductByIdFinder finder;

    public GetProductProvidersRestController(ProductByIdFinder finder) {
        this.finder = finder;
    }

    @GetMapping("/api/products/{id}/providers")
    public ResponseEntity<ProductProvidersResponse> handle(@PathVariable String id){
        var result = this.finder.executeProviders(id);

        return ResponseEntity.ok(result);
    }


}
