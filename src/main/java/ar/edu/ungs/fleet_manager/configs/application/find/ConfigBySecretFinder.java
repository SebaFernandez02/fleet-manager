package ar.edu.ungs.fleet_manager.configs.application.find;

import ar.edu.ungs.fleet_manager.configs.application.ConfigResponse;
import ar.edu.ungs.fleet_manager.configs.domain.Config;
import ar.edu.ungs.fleet_manager.configs.domain.services.ConfigFinder;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import org.springframework.stereotype.Component;

@Component
public final class ConfigBySecretFinder {
    private final ConfigFinder finder;


    public ConfigBySecretFinder(ConfigFinder finder) {
        this.finder = finder;
    }

    public ConfigResponse execute(EnterpriseId enterpriseId){
        Config api = this.finder.execute( enterpriseId);
        return ConfigResponse.map(api);

    }
}
