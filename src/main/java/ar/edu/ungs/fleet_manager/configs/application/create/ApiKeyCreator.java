package ar.edu.ungs.fleet_manager.configs.application.create;

import ar.edu.ungs.fleet_manager.configs.application.ApiKeyRequest;
import ar.edu.ungs.fleet_manager.configs.domain.ApiKey;
import ar.edu.ungs.fleet_manager.configs.domain.ApiKeyRepository;
import ar.edu.ungs.fleet_manager.configs.domain.ApiKeyType;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import org.springframework.stereotype.Component;

@Component
public final class ApiKeyCreator {
    private final ApiKeyRepository repository;

    public ApiKeyCreator(ApiKeyRepository repository) {
        this.repository = repository;
    }

    public void execute(ApiKeyRequest request, EnterpriseId enterpriseId){
        ApiKey apiKey = new ApiKey(ApiKeyType.parse(request.key()), request.value(), request.secret(), enterpriseId);

        this.repository.save(apiKey);
    }
}
