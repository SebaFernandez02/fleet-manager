package ar.edu.ungs.fleet_manager.controls.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

import java.util.Locale;

public enum ControlPriority {
    HIGH,
    MEDIUM,
    LOW;

    public static ControlPriority parse(String priority){
        try {
            return valueOf(priority.toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw new InvalidParameterException("Invalid priority: " + priority);
        }
    }
}
