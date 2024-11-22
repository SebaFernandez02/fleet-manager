package ar.edu.ungs.fleet_manager.configs.application;

import ar.edu.ungs.fleet_manager.configs.domain.Config;

public record ConfigResponse(String key, String value) {

    public static ConfigResponse map(Config config){
        return new ConfigResponse(config.type().name(), config.key());
    }
}
