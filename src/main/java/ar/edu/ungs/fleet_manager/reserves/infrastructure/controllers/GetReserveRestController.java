package ar.edu.ungs.fleet_manager.reserves.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.reserves.application.ReserveResponse;
import ar.edu.ungs.fleet_manager.reserves.application.find.ReserveByIdFinder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetReserveRestController {
    private final ReserveByIdFinder finder;

    public GetReserveRestController(ReserveByIdFinder finder) {
        this.finder = finder;
    }

    @GetMapping("/api/reserves/{id}")
    public ResponseEntity<ReserveResponse> handle(@PathVariable String id) {
        var result = this.finder.execute(id);

        return ResponseEntity.ok(result);
    }
}
