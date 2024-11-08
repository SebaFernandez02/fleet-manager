package ar.edu.ungs.fleet_manager.products.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import ar.edu.ungs.fleet_manager.products.application.ProductResponse;
import ar.edu.ungs.fleet_manager.products.application.search.ProductsAllSearcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetProductsRestController {
    private final ProductsAllSearcher searcher;

    public GetProductsRestController(ProductsAllSearcher searcher) {
        this.searcher = searcher;
    }

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductResponse>> handle(@RequestParam("enterprise_id") String enterpriseId){
        var result = this.searcher.execute(new EnterpriseId(enterpriseId));

        return ResponseEntity.ok(result);
    }
}
