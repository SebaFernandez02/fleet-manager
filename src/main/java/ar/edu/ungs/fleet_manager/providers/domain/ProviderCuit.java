package ar.edu.ungs.fleet_manager.providers.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record ProviderCuit(String value) {
    public ProviderCuit {
        if (value == null || value.isBlank()) {
            throw new InvalidParameterException("the email value is invalid");
        }
    }
}
