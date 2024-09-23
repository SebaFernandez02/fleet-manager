package ar.edu.ungs.fleet_manager.products.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record ProductBrand(String value) {
    public ProductBrand {
        if (value == null || value.isBlank()) {
            throw new InvalidParameterException("the product brand cannot be null or empty");
        }
    }
}
