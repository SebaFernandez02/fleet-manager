package ar.edu.ungs.fleet_manager.users.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.users.application.UserRequest;
import ar.edu.ungs.fleet_manager.users.application.assign.UserRoleAssigner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PutUserRoleRestController {
    private final UserRoleAssigner assigner;

    public PutUserRoleRestController(UserRoleAssigner assigner) {
        this.assigner = assigner;
    }

    @PutMapping("/api/users/{id}/roles")
    public ResponseEntity<?> handle(@PathVariable String id, @RequestBody UserRequest request) {
        this.assigner.execute(id, request);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
