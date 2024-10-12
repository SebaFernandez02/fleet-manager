package ar.edu.ungs.fleet_manager.controls.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record ControlAssigned (String value){


    @Override
    public String value() {
        return value;
    }
}
