package ar.edu.ungs.fleet_manager.products.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.products.application.search.ProductsByProviderSearcher;
import ar.edu.ungs.fleet_manager.providers.application.search.ProviderByProductSearcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetProductProvidersRestController {
    private final ProviderByProductSearcher searcher;

    public GetProductProvidersRestController(ProviderByProductSearcher searcher) {
        this.searcher = searcher;
    }

    //ESTE

    @GetMapping("/api/products/{id}/providers")
    public ResponseEntity<?> handle(@PathVariable String id){
        var result = this.searcher.execute(id);

        return ResponseEntity.ok(result);
    }
}
