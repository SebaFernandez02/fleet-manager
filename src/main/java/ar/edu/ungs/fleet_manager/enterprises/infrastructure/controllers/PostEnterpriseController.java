package ar.edu.ungs.fleet_manager.enterprises.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.enterprises.application.EnterpriseRequest;
import ar.edu.ungs.fleet_manager.enterprises.application.create.EnterpriseCreator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostEnterpriseController {
    private final EnterpriseCreator creator;

    public PostEnterpriseController(EnterpriseCreator creator) {
        this.creator = creator;
    }

    @PostMapping("/api/enterprises")
    public ResponseEntity<?> handle(@RequestBody EnterpriseRequest request) {
        this.creator.execute(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
