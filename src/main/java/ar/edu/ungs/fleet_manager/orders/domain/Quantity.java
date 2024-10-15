package ar.edu.ungs.fleet_manager.orders.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record Quantity(Integer value) {
    public Quantity {
        if (value == null || value < 0) {
            throw new InvalidParameterException("the quantity value is invalid");
        }
    }
}
