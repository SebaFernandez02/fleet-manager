package ar.edu.ungs.fleet_manager.users.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.users.application.UserRequest;
import ar.edu.ungs.fleet_manager.users.application.UserResponse;
import ar.edu.ungs.fleet_manager.users.application.auth.UserAuthenticator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostUserAuthenticationsRestController {
    private final UserAuthenticator authenticator;

    public PostUserAuthenticationsRestController(UserAuthenticator authenticator) {
        this.authenticator = authenticator;
    }

    @PostMapping("/api/users/auths")
    public ResponseEntity<?> handle(@RequestBody UserRequest request) {
        UserResponse response = this.authenticator.execute(request);

        return ResponseEntity.ok(response);
    }
}
