package ar.edu.ungs.fleet_manager.orders.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.orders.application.AddOrderProductRequest;
import ar.edu.ungs.fleet_manager.orders.application.update.OrderProductAdder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PutOrderRestController {
    private final OrderProductAdder adder;

    public PutOrderRestController(OrderProductAdder adder) {
        this.adder = adder;
    }

    @PutMapping("/api/orders/products")
    public ResponseEntity<?> handle(@PathVariable String id, @PathVariable AddOrderProductRequest request) {
        this.adder.execute(request);

        return ResponseEntity.ok().build();
    }
}
