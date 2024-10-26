package ar.edu.ungs.fleet_manager.enterprises.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record EnterpriseName(String value) {
    public EnterpriseName {
        if(value == null || value.isEmpty()){
            throw new InvalidParameterException("the name cannot be null or empty");
        }
    }
}
