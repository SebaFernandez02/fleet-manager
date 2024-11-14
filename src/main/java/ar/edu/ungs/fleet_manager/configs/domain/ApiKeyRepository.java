package ar.edu.ungs.fleet_manager.configs.domain;

import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;

import java.util.List;
import java.util.Optional;

public interface ApiKeyRepository {
    void save(ApiKey apiKey);

    Optional<ApiKey> findByType(ApiKeyType type, EnterpriseId enterpriseId);

    List<ApiKey> searchAll(EnterpriseId enterpriseId);

    Optional<ApiKey>  findBySecret(EnterpriseId enterpriseId);
}

