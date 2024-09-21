package ar.edu.ungs.fleet_manager.users.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.users.application.UserRequest;
import ar.edu.ungs.fleet_manager.users.application.password.UserPasswordChanger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PutUserPasswordRestController {
    private final UserPasswordChanger changer;

    public PutUserPasswordRestController(UserPasswordChanger changer) {
        this.changer = changer;
    }

    @PutMapping("/api/users/{id}/passwords")
    public ResponseEntity<?> handle(@PathVariable String id, @RequestBody UserRequest request) {
        this.changer.execute(id, request);

        return ResponseEntity.ok().build();
    }
}
