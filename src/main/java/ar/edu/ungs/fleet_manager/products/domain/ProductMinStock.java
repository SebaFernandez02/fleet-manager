package ar.edu.ungs.fleet_manager.products.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record ProductMinStock(Integer value) {
    public ProductMinStock {
        if(value == null || value < 0){
            throw new InvalidParameterException("the productId minimum Stock cannot be null or negative");
        }
    }
}
