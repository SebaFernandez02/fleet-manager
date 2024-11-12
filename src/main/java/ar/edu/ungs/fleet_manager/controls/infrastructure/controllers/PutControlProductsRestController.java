package ar.edu.ungs.fleet_manager.controls.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.controls.application.ControlAddProductRequest;
import ar.edu.ungs.fleet_manager.controls.application.update.ControlProductAdder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class PutControlProductsRestController {
    private final ControlProductAdder updater;

    public PutControlProductsRestController(ControlProductAdder updater) {
        this.updater = updater;
    }

    @PutMapping ("/api/controls/{id}/products")
    public ResponseEntity<?> handle(@PathVariable String id, @RequestBody ControlAddProductRequest request){

        this.updater.execute(id, request);

        return ResponseEntity.ok().build();
    }
}
