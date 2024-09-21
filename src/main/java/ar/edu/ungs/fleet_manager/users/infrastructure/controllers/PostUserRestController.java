package ar.edu.ungs.fleet_manager.users.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.users.application.UserRequest;
import ar.edu.ungs.fleet_manager.users.application.register.UserRegistrar;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostUserRestController {
    private final UserRegistrar registrar;

    public PostUserRestController(UserRegistrar registrar) {
        this.registrar = registrar;
    }

    @PostMapping("/api/users")
    public ResponseEntity<?> handle(@RequestBody UserRequest request) {
        this.registrar.execute(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
