package ar.edu.ungs.fleet_manager.vehicles.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record VehicleModel(String value) {
    public VehicleModel {
        if (value == null || value.isBlank()) {
            throw new InvalidParameterException("the brand value is invalid");
        }
    }
}
