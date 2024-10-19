package ar.edu.ungs.fleet_manager.products.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.products.application.ProductRequest;
import ar.edu.ungs.fleet_manager.products.application.create.ProductCreator;
import ar.edu.ungs.fleet_manager.products.application.update.ProductUpdater;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PutProductRestController {
    private final ProductUpdater updater;

    public PutProductRestController(ProductUpdater updater) {
        this.updater = updater;
    }

    @PutMapping("/api/products/{id}")
    public ResponseEntity<?> handle(@PathVariable String id, @RequestBody ProductRequest request){
        this.updater.execute(id, request);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
