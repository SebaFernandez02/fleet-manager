package ar.edu.ungs.fleet_manager.products.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.products.application.ProductRequest;
import ar.edu.ungs.fleet_manager.products.application.create.ProductCreator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostProductRestController {

    private final ProductCreator creator;


    public PostProductRestController(ProductCreator creator) {
        this.creator = creator;
    }

    @PostMapping("/api/products")
    public ResponseEntity<?> handle(@RequestBody ProductRequest request){
        this.creator.execute(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
