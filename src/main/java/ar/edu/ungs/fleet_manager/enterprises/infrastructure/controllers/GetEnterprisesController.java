package ar.edu.ungs.fleet_manager.enterprises.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.enterprises.application.search.EnterpriseSearcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetEnterprisesController {
    private final EnterpriseSearcher searcher;

    public GetEnterprisesController(EnterpriseSearcher searcher) {
        this.searcher = searcher;
    }

    @GetMapping("/api/enterprises")
    public ResponseEntity<?> handle() {
        var result = this.searcher.execute();

        return ResponseEntity.ok(result);
    }
}
