package ar.edu.ungs.fleet_manager.products.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record ProductDescription(String value) {
    public ProductDescription {
        if(value == null || value.isEmpty()){
            throw new InvalidParameterException("the productId description cannot be null or empty");
        }
    }
}
