package ar.edu.ungs.fleet_manager.products.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record ProductName(String value) {
    public ProductName {
        if (value == null || value.isBlank()) {
            throw new InvalidParameterException("the productId name cannot be null or empty");
        }
    }


}
