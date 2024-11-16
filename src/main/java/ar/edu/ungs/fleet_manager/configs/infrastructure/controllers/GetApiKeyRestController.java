package ar.edu.ungs.fleet_manager.configs.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.configs.application.ApiKeyResponse;
import ar.edu.ungs.fleet_manager.configs.application.find.ApiKeyBySecretFinder;
import ar.edu.ungs.fleet_manager.configs.application.find.ApiKeyByTypeFinder;
import ar.edu.ungs.fleet_manager.configs.domain.ApiKeyType;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class GetApiKeyRestController {
    private final ApiKeyBySecretFinder finder;

    public GetApiKeyRestController(ApiKeyBySecretFinder finder) {
        this.finder = finder;
    }

    @GetMapping ("/api/enterprises/{enterpriseId}/configs")
    public ResponseEntity<?> handle(@PathVariable String enterpriseId){
        ApiKeyResponse response = this.finder.execute(new EnterpriseId(enterpriseId));

        return ResponseEntity.ok(response);
    }
}
