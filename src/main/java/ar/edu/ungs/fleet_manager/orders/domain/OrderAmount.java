package ar.edu.ungs.fleet_manager.orders.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

import java.math.BigDecimal;

public record OrderAmount(BigDecimal value){
    public OrderAmount {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidParameterException("the amount value is invalid");
        }
    }
}
