package ar.edu.ungs.fleet_manager.configs.application.find;

import ar.edu.ungs.fleet_manager.configs.application.ApiKeyResponse;
import ar.edu.ungs.fleet_manager.configs.domain.ApiKey;
import ar.edu.ungs.fleet_manager.configs.domain.ApiKeyType;
import ar.edu.ungs.fleet_manager.configs.domain.services.ApiKeyFinder;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import org.springframework.stereotype.Component;

@Component
public final class ApiKeyBySecretFinder {
    private final ApiKeyFinder finder;


    public ApiKeyBySecretFinder(ApiKeyFinder finder) {
        this.finder = finder;
    }

    public ApiKeyResponse execute(EnterpriseId enterpriseId){
        ApiKey api = this.finder.execute( enterpriseId);
        return ApiKeyResponse.map(api);

    }
}
