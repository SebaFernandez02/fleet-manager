package ar.edu.ungs.fleet_manager.orders.infrastructure.controllers;


import ar.edu.ungs.fleet_manager.orders.application.OrderResponse;
import ar.edu.ungs.fleet_manager.orders.application.find.OrderByIdFinder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetOrderRestController {
    private OrderByIdFinder finder;

    public GetOrderRestController(OrderByIdFinder finder) {
        this.finder = finder;
    }

    @GetMapping ("/api/orders/{id}")
    public ResponseEntity<?> handle(@PathVariable String id){
        OrderResponse result = this.finder.execute(id);

        return ResponseEntity.ok(result);
    }
}
