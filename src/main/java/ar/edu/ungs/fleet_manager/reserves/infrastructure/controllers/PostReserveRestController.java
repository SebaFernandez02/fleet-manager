package ar.edu.ungs.fleet_manager.reserves.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.reserves.application.ReserveRequest;
import ar.edu.ungs.fleet_manager.reserves.application.create.ReserveCreator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostReserveRestController {
    private final ReserveCreator creator;

    public PostReserveRestController(ReserveCreator creator) {
        this.creator = creator;
    }

    @PostMapping("/api/reserves")
    public ResponseEntity<?> handle(@RequestBody ReserveRequest request) {
        this.creator.execute(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
