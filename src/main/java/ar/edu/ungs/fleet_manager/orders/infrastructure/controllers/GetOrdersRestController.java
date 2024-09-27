package ar.edu.ungs.fleet_manager.orders.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.orders.application.OrderResponse;
import ar.edu.ungs.fleet_manager.orders.application.search.OrderAllSearcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetOrdersRestController {
    private final OrderAllSearcher searcher;

    public GetOrdersRestController(OrderAllSearcher searcher) {
        this.searcher = searcher;
    }

    @GetMapping ("/api/orders")
    public ResponseEntity<List<OrderResponse>> handle(){
        List<OrderResponse> result = this.searcher.execute();

        return ResponseEntity.ok(result);
    }
}
