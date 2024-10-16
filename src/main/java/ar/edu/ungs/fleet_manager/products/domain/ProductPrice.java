package ar.edu.ungs.fleet_manager.products.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

import java.math.BigDecimal;

public record ProductPrice(BigDecimal value) {
    public ProductPrice {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidParameterException("the price value is invalid");
        }
    }
}
