package ar.edu.ungs.fleet_manager.configs.application.search;

import ar.edu.ungs.fleet_manager.configs.application.ConfigResponse;
import ar.edu.ungs.fleet_manager.configs.domain.Config;
import ar.edu.ungs.fleet_manager.configs.domain.ConfigRepository;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ConfigAllSearcher {
    private final ConfigRepository repository;

    public ConfigAllSearcher(ConfigRepository repository) {
        this.repository = repository;
    }

    public List<ConfigResponse> execute(EnterpriseId enterpriseId){
        return this.repository.searchAll(enterpriseId).stream().map(this::apply).toList();
    }

    private ConfigResponse apply(Config config) {
        return ConfigResponse.map(config);
    }
}

