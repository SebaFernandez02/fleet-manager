package ar.edu.ungs.fleet_manager.configs.application.search;

import ar.edu.ungs.fleet_manager.configs.application.ApiKeyResponse;
import ar.edu.ungs.fleet_manager.configs.domain.ApiKey;
import ar.edu.ungs.fleet_manager.configs.domain.ApiKeyRepository;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ApiKeyAllSearcher {
    private final ApiKeyRepository repository;

    public ApiKeyAllSearcher(ApiKeyRepository repository) {
        this.repository = repository;
    }

    public List<ApiKeyResponse> execute(EnterpriseId enterpriseId){
        return this.repository.searchAll(enterpriseId).stream().map(this::apply).toList();
    }

    private ApiKeyResponse apply(ApiKey apiKey) {
        return ApiKeyResponse.map(apiKey);
    }
}

