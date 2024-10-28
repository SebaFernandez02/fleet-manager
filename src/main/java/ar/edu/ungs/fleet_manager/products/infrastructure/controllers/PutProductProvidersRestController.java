package ar.edu.ungs.fleet_manager.products.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.products.application.ProductProviderRequest;
import ar.edu.ungs.fleet_manager.products.application.update.ProductProviderUpdater;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PutProductProvidersRestController {
    private final ProductProviderUpdater updater;

    public PutProductProvidersRestController(ProductProviderUpdater updater) {
        this.updater = updater;
    }

    @PutMapping("/api/products/{id}/providers")
    public ResponseEntity<?> handle(@PathVariable String id, @RequestBody ProductProviderRequest request){
        this.updater.execute(id, request);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
