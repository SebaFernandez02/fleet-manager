package ar.edu.ungs.fleet_manager.vehicles.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

import java.util.Locale;

public enum VehicleStatus {
    AVAILABLE,
    RESERVED,
    MAINTENANCE,
    UNAVAILABLE;

    public static VehicleStatus parse(String status) {
        try {
            return valueOf(status.toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw new InvalidParameterException("status invalid");
        }
    }
}
