package ar.edu.ungs.fleet_manager.vehicles.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record VehicleColor(String value) {
    public VehicleColor {
        if (value == null || value.isBlank()) {
            throw new InvalidParameterException("the color value is invalid");
        }
    }
}
