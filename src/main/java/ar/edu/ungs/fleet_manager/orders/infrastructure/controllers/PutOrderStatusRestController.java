package ar.edu.ungs.fleet_manager.orders.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.orders.application.update.OrderStatusUpdater;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PutOrderStatusRestController {
    private final OrderStatusUpdater updater;

    public PutOrderStatusRestController(OrderStatusUpdater updater) {
        this.updater = updater;
    }

    @PutMapping("/api/orders/{id}/status/{status}")
    public ResponseEntity<?> handle(@PathVariable String id, @PathVariable String status) {
        this.updater.execute(id, status);

        return ResponseEntity.ok().build();
    }
}
