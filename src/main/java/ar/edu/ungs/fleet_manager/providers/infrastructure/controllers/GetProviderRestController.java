package ar.edu.ungs.fleet_manager.providers.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.providers.application.ProviderResponse;
import ar.edu.ungs.fleet_manager.providers.application.find.ProviderByIdFinder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetProviderRestController {
    private final ProviderByIdFinder finder;

    public GetProviderRestController(ProviderByIdFinder finder) {
        this.finder = finder;
    }

    @GetMapping("/api/providers/{id}")
    public ResponseEntity<?> handle (@PathVariable String id){
        ProviderResponse result = this.finder.execute(id);

        return ResponseEntity.ok(result);
    }

}
