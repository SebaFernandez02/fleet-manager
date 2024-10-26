package ar.edu.ungs.fleet_manager.enterprises.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.enterprises.application.find.EnterpriseByIdFinder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetEnterpriseController {
    private final EnterpriseByIdFinder finder;

    public GetEnterpriseController(EnterpriseByIdFinder finder) {
        this.finder = finder;
    }

    @GetMapping("/api/enterprises/{id}")
    public ResponseEntity<?> handle(@PathVariable String id) {
        var result = this.finder.execute(id);

        return ResponseEntity.ok(result);
    }
}
