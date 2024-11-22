package ar.edu.ungs.fleet_manager.configs.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.configs.application.ConfigResponse;
import ar.edu.ungs.fleet_manager.configs.application.find.ConfigBySecretFinder;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class GetApiKeyRestController {
    private final ConfigBySecretFinder finder;

    public GetApiKeyRestController(ConfigBySecretFinder finder) {
        this.finder = finder;
    }

    @GetMapping ("/api/enterprises/{enterpriseId}/configs")
    public ResponseEntity<?> handle(@PathVariable String enterpriseId){
        ConfigResponse response = this.finder.execute(new EnterpriseId(enterpriseId));

        return ResponseEntity.ok(response);
    }
}
