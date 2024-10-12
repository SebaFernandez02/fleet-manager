package ar.edu.ungs.fleet_manager.controls.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record ControlDescription (String value){
    public ControlDescription{
        if(value.isBlank()){
            throw new InvalidParameterException("the description value is invalid");
        }
    }

    @Override
    public String value() {
        return value;
    }
}
