package ar.edu.ungs.fleet_manager.configs.domain.services;

import ar.edu.ungs.fleet_manager.configs.domain.Config;
import ar.edu.ungs.fleet_manager.configs.domain.ConfigRepository;
import ar.edu.ungs.fleet_manager.configs.domain.ConfigType;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public final class ConfigFinder {
    private final ConfigRepository repository;

    public ConfigFinder(ConfigRepository repository) {
        this.repository = repository;
    }

    public Config execute(ConfigType type, EnterpriseId enterpriseId){
        return this.repository.findByType(type, enterpriseId).orElseThrow(() -> new NotFoundException(String.format("api type %s not found", type.name())));
    }

    public Config execute(EnterpriseId enterpriseId){
        return this.repository.findBySecret( enterpriseId).orElseThrow(() -> new NotFoundException("apikey not found"));
    }
}
