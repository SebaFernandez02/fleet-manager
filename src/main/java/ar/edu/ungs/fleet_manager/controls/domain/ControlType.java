package ar.edu.ungs.fleet_manager.controls.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

import java.util.Locale;

public enum ControlType {
    PREVENTIVE,
    CORRECTIVE,
    PREDICTIVE;

    public static ControlType parse(String type){
        try {
            return valueOf(type.toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw new InvalidParameterException("Invalid type: " + type);
        }
    }

}
