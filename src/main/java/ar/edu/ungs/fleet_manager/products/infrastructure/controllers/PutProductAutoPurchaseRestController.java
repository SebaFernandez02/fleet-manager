package ar.edu.ungs.fleet_manager.products.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.products.application.ProductRequest;
import ar.edu.ungs.fleet_manager.products.application.update.ProductUpdater;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PutProductAutoPurchaseRestController {
    private final ProductUpdater updater;

    public PutProductAutoPurchaseRestController(ProductUpdater updater) {
        this.updater = updater;
    }

    @PutMapping("/api/products/{id}/autoPurchase/{value}")
    public ResponseEntity<?> handle(@PathVariable String id, @PathVariable String value){
        this.updater.execute(id, value);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
