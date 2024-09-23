package ar.edu.ungs.fleet_manager.products.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record ProductCategory(String value) {
    public ProductCategory {
        if (value == null || value.isBlank()) {
            throw new InvalidParameterException("the product category is invalid");
        }
    }
}
