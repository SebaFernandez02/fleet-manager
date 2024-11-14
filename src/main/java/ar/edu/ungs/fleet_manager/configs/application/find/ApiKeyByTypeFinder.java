package ar.edu.ungs.fleet_manager.configs.application.find;

import ar.edu.ungs.fleet_manager.configs.application.ApiKeyResponse;
import ar.edu.ungs.fleet_manager.configs.domain.ApiKey;
import ar.edu.ungs.fleet_manager.configs.domain.ApiKeyRepository;
import ar.edu.ungs.fleet_manager.configs.domain.ApiKeyType;
import ar.edu.ungs.fleet_manager.configs.domain.services.ApiKeyFinder;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class ApiKeyByTypeFinder {
    private final ApiKeyFinder finder;

    public ApiKeyByTypeFinder(ApiKeyFinder finder) {
        this.finder = finder;
    }

    public ApiKeyResponse execute(String apiKey, EnterpriseId enterpriseId){
        ApiKey api = this.finder.execute(ApiKeyType.parse(apiKey), enterpriseId);
        return ApiKeyResponse.map(api);

    }
}
