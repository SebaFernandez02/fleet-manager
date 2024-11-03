package ar.edu.ungs.fleet_manager.controls.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.controls.application.ControlResponse;
import ar.edu.ungs.fleet_manager.controls.application.find.ControlByIdFinder;
import ar.edu.ungs.fleet_manager.controls.application.update.ControlProductDeleter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class DeleteControlProductsRestController {
    private final ControlProductDeleter deleter;

    public DeleteControlProductsRestController(ControlProductDeleter deleter) {
        this.deleter = deleter;
    }

    @DeleteMapping ("/api/controls/{id}/products")
    public ResponseEntity<?> handle(@PathVariable String id) {

        this.deleter.execute(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();

    }
}
