package ar.edu.ungs.fleet_manager.configs.domain.services;

import ar.edu.ungs.fleet_manager.configs.domain.ApiKey;
import ar.edu.ungs.fleet_manager.configs.domain.ApiKeyRepository;
import ar.edu.ungs.fleet_manager.configs.domain.ApiKeyType;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public final class ApiKeyFinder {
    private final ApiKeyRepository repository;

    public ApiKeyFinder(ApiKeyRepository repository) {
        this.repository = repository;
    }

    public ApiKey execute(ApiKeyType type, EnterpriseId enterpriseId){
        return this.repository.findByType(type, enterpriseId).orElseThrow(() -> new NotFoundException(String.format("api type %s not found", type.name())));
    }

    public ApiKey execute(EnterpriseId enterpriseId){
        return this.repository.findBySecret( enterpriseId).orElseThrow(() -> new NotFoundException("apikey not found"));
    }
}
