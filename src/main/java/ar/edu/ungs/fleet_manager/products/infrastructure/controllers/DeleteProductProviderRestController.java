package ar.edu.ungs.fleet_manager.products.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.products.application.update.ProductProviderUpdater;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DeleteProductProviderRestController {
    private final ProductProviderUpdater updater;

    public DeleteProductProviderRestController(ProductProviderUpdater updater) {
        this.updater = updater;
    }

    @DeleteMapping("/api/products/{productId}/providers/{providerId}")
    public ResponseEntity<?> handle(@PathVariable String productId, @PathVariable String providerId){
        this.updater.delete(productId, providerId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
