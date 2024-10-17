package ar.edu.ungs.fleet_manager.vehicles.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record VehicleAxles(Integer value) {
    public VehicleAxles {
        if (value == null || value <= 0) {
            throw new InvalidParameterException("invalid vehicle axles");
        }
    }
}
