package ar.edu.ungs.fleet_manager.configs.application;

public record ApiKeyRequest (String key, String value, boolean secret){
}
