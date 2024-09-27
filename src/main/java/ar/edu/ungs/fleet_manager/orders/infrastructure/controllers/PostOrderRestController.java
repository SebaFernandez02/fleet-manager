package ar.edu.ungs.fleet_manager.orders.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.orders.application.OrderRequest;
import ar.edu.ungs.fleet_manager.orders.application.create.OrderCreator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostOrderRestController {
    private OrderCreator creator;

    public PostOrderRestController(OrderCreator creator) {
        this.creator = creator;
    }

    @PostMapping ("/api/orders")
    public ResponseEntity<?> handle (@RequestBody OrderRequest request){
        this.creator.execute(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
