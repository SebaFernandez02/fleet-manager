package ar.edu.ungs.fleet_manager.providers.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record ProviderName(String value) {
    public ProviderName {
        if (value == null || value.isBlank()) {
            throw new InvalidParameterException("the name value is invalid");
        }
    }
}