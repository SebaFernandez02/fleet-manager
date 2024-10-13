package ar.edu.ungs.fleet_manager.controls.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

import java.util.Locale;

public enum ControlStatus {
    TODO,
    DOING,
    DONE;

    public static ControlStatus parse(String status){
        try{
            return valueOf(status.toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw new InvalidParameterException("Invalid status: " + status);
        }

    }
}
