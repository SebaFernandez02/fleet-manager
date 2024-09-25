package ar.edu.ungs.fleet_manager.providers.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.providers.application.ProviderResponse;
import ar.edu.ungs.fleet_manager.providers.application.search.ProviderAllSearcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetProvidersRestController {
    private final ProviderAllSearcher searcher;

    public GetProvidersRestController(ProviderAllSearcher searcher) {
        this.searcher = searcher;
    }

    @GetMapping("/api/providers")
    public ResponseEntity<List<ProviderResponse>> handle() {
        List<ProviderResponse> result = this.searcher.execute();

        return ResponseEntity.ok(result);
    }
}
