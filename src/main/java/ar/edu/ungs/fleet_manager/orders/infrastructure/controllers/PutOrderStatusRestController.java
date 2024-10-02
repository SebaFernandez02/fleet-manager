package ar.edu.ungs.fleet_manager.orders.infrastructure.controllers;




import ar.edu.ungs.fleet_manager.orders.application.OrderStatusRequest;
import ar.edu.ungs.fleet_manager.orders.application.update.OrderUpdater;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PutOrderStatusRestController {

    private final OrderUpdater updater;

    public PutOrderStatusRestController(OrderUpdater updater) {
        this.updater = updater;
    }

    @PutMapping("api/orders/{id}/status")
    public ResponseEntity<?> handle(@PathVariable String id, @RequestBody OrderStatusRequest status) {

        this.updater.execute(id,status);

        return ResponseEntity.ok().build();

    }

}
