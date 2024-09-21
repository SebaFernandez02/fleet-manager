package ar.edu.ungs.fleet_manager.users.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.users.application.UserResponse;
import ar.edu.ungs.fleet_manager.users.application.find.UserByIdFinder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetUserRestController {
    private final UserByIdFinder finder;

    public GetUserRestController(UserByIdFinder finder) {
        this.finder = finder;
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<?> handle(@PathVariable String id) {
        UserResponse response = this.finder.execute(id);

        return ResponseEntity.ok(response);
    }
}
