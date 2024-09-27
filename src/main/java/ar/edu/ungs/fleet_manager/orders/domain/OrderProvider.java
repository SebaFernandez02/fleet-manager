package ar.edu.ungs.fleet_manager.orders.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record OrderProvider(String value) {
    public OrderProvider {
        if (value == null || value.isBlank()) {
            throw new InvalidParameterException("the provider value is invalid");
        }
    }

    @Override
    public String value() {
        return value;
    }
}
