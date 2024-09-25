package ar.edu.ungs.fleet_manager.providers.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.providers.application.ProviderRequest;
import ar.edu.ungs.fleet_manager.providers.application.create.ProviderCreator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostProviderRestController {
    private final ProviderCreator creator;

    public PostProviderRestController(ProviderCreator creator) {
        this.creator = creator;
    }

    @PostMapping("/api/providers")
    public ResponseEntity<?> handle(@RequestBody ProviderRequest request) {
        this.creator.execute(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
