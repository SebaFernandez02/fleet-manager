package ar.edu.ungs.fleet_manager.vehicles.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record VehicleFuelConsumption(Integer value) {
    public VehicleFuelConsumption {
        if (value == null || value <= 0) {
            throw new InvalidParameterException("invalid vehicle consumption");
        }
    }
}
