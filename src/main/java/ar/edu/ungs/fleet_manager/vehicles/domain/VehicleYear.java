package ar.edu.ungs.fleet_manager.vehicles.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record VehicleYear(Integer value) {
    public VehicleYear {
        if (value == null || value <= 0) {
            throw new InvalidParameterException("invalid vehicle year");
        }
    }
}
