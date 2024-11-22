package ar.edu.ungs.fleet_manager.configs.application.create;

import ar.edu.ungs.fleet_manager.configs.application.ConfigRequest;
import ar.edu.ungs.fleet_manager.configs.domain.Config;
import ar.edu.ungs.fleet_manager.configs.domain.ConfigRepository;
import ar.edu.ungs.fleet_manager.configs.domain.ConfigType;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import org.springframework.stereotype.Component;

@Component
public final class ConfigCreator {
    private final ConfigRepository repository;

    public ConfigCreator(ConfigRepository repository) {
        this.repository = repository;
    }

    public void execute(ConfigRequest request, EnterpriseId enterpriseId){
        Config config = new Config(ConfigType.parse(request.key()), request.value(), request.secret(), enterpriseId);

        this.repository.save(config);
    }
}
