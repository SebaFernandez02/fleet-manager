package ar.edu.ungs.fleet_manager.providers.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.products.application.search.ProductsByProviderSearcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetProviderProductsRestController {
    private final ProductsByProviderSearcher searcher;

    public GetProviderProductsRestController(ProductsByProviderSearcher searcher) {
        this.searcher = searcher;
    }

    @GetMapping("/api/providers/{id}/products")
    public ResponseEntity<?> handle(@PathVariable String id){
        var result = this.searcher.execute(id);

        return ResponseEntity.ok(result);
    }
}
