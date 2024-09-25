package ar.edu.ungs.fleet_manager.providers.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record ProviderEmail(String value) {
    public ProviderEmail {
        if (value == null || value.isBlank()) {
            throw new InvalidParameterException("the brand value is invalid");
        }
    }
}
