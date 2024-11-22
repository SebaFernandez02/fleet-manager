package ar.edu.ungs.fleet_manager.configs.domain;

import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;

import java.util.List;
import java.util.Optional;

public interface ConfigRepository {
    void save(Config config);

    Optional<Config> findByType(ConfigType type, EnterpriseId enterpriseId);

    List<Config> searchAll(EnterpriseId enterpriseId);

    Optional<Config>  findBySecret(EnterpriseId enterpriseId);
}

