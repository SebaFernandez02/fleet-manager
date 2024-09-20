package ar.edu.ungs.fleet_manager.vehicles.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record VehicleId(String value) {
    public VehicleId {
        if (value == null || value.isBlank()) {
            throw new InvalidParameterException("the vehicle identifier is invalid");
        }

        if (value.contains(" ")) {
            throw new InvalidParameterException("the vehicle identifier is invalid because contains spaces");
        }
    }
}
