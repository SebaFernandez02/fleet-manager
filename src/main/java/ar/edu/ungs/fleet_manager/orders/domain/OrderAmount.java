package ar.edu.ungs.fleet_manager.orders.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record OrderAmount(Integer value){
    public OrderAmount {
        if (value == null || value <= 0) {
            throw new InvalidParameterException("the amount value is invalid");
        }
    }
}
