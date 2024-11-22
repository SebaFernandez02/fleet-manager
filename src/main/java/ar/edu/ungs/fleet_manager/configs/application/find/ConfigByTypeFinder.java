package ar.edu.ungs.fleet_manager.configs.application.find;

import ar.edu.ungs.fleet_manager.configs.application.ConfigResponse;
import ar.edu.ungs.fleet_manager.configs.domain.Config;
import ar.edu.ungs.fleet_manager.configs.domain.ConfigType;
import ar.edu.ungs.fleet_manager.configs.domain.services.ConfigFinder;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import org.springframework.stereotype.Component;

@Component
public final class ConfigByTypeFinder {
    private final ConfigFinder finder;

    public ConfigByTypeFinder(ConfigFinder finder) {
        this.finder = finder;
    }

    public ConfigResponse execute(String config, EnterpriseId enterpriseId){
        Config api = this.finder.execute(ConfigType.parse(config), enterpriseId);
        return ConfigResponse.map(api);

    }
}
