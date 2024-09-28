package ar.edu.ungs.fleet_manager.products.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record ProductId (String value) {
    public ProductId {
        if (value == null || value.isBlank()) {
            throw new InvalidParameterException("the productId identifier is invalid");
        }
    }
}
