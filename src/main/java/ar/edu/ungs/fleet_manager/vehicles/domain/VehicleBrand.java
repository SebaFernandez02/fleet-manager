package ar.edu.ungs.fleet_manager.vehicles.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record VehicleBrand(String value) {
    public VehicleBrand {
        if (value == null || value.isBlank()) {
            throw new InvalidParameterException("the brand value is invalid");
        }
    }
}
