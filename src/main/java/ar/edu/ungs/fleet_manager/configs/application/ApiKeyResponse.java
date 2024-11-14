package ar.edu.ungs.fleet_manager.configs.application;

import ar.edu.ungs.fleet_manager.configs.domain.ApiKey;
import ar.edu.ungs.fleet_manager.configs.domain.ApiKeyType;

public record ApiKeyResponse(String key, String value) {

    public static ApiKeyResponse map(ApiKey apiKey){
        return new ApiKeyResponse(apiKey.type().name(), apiKey.key());
    }
}
