package ar.edu.ungs.fleet_manager.products.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record ProductQuantity(Integer value) {
    public ProductQuantity {
        if(value == null || value < 0){
            throw new InvalidParameterException("the product quantity cannot be null or negative");
        }
    }
}
