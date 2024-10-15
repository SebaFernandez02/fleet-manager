package ar.edu.ungs.fleet_manager.orders.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.orders.application.update.OrderStatusUpdater;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PutOrderRestController {
    private final OrderStatusUpdater updater;

    public PutOrderRestController(OrderStatusUpdater updater) {
        this.updater = updater;
    }

    @PutMapping("/api/orders/{id}")
    public ResponseEntity<?> handle(@PathVariable String id, @PathVariable String status) {
        this.updater.execute(id, status);

        return ResponseEntity.ok().build();
    }
}
