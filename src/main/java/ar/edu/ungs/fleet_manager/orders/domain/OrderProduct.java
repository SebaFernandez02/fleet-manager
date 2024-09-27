package ar.edu.ungs.fleet_manager.orders.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record OrderProduct(String value) {
    public OrderProduct {
        if (value == null || value.isBlank()) {
            throw new InvalidParameterException("the product value is invalid");
        }
    }

    @Override
    public String value() {
        return value;
    }
}
