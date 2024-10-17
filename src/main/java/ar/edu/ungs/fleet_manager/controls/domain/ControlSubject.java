package ar.edu.ungs.fleet_manager.controls.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record ControlSubject(String value) {
    public ControlSubject{
        if(value.isBlank()){
            throw new InvalidParameterException("the subject value is invalid");
        }
    }
}
