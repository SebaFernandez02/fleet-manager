package ar.edu.ungs.fleet_manager.providers.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record ProviderAddress(String value) {
    public ProviderAddress {
        if (value == null || value.isBlank()) {
            throw new InvalidParameterException("the address value is invalid");
        }
    }
}
