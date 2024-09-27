package ar.edu.ungs.fleet_manager.providers.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record ProviderPhoneNumber(String value) {
    public ProviderPhoneNumber {
        if (value == null || value.isBlank()) {
            throw new InvalidParameterException("the phone number value is invalid");
        }
    }
}
