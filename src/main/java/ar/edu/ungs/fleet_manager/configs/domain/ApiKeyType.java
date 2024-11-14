package ar.edu.ungs.fleet_manager.configs.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

import java.util.Locale;


public enum ApiKeyType {
    GOOGLE_DIRECTIONS_KEY,
    OPSGENIE_LINK,
    OPSGENIE_KEY;

    public static ApiKeyType parse(String keyType){
        try {
            return valueOf(keyType.toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw new InvalidParameterException("Invalid key type: " + keyType);
        }
    }
}
