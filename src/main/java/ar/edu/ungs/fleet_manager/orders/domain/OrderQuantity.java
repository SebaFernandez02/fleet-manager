package ar.edu.ungs.fleet_manager.orders.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record OrderQuantity(Integer value) {

    public OrderQuantity {
        if (value == null || value <= 0) {
            throw new InvalidParameterException("the quantity value is invalid");
        }
    }

    @Override
    public Integer value() {
        return value;
    }
}
