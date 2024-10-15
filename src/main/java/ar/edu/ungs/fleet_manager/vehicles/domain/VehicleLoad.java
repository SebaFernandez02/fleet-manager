package ar.edu.ungs.fleet_manager.vehicles.domain;


import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record VehicleLoad(Integer value) {
    public VehicleLoad {
        if (value < 0) {
            throw new InvalidParameterException("invalid vehicle load");
        }
    }
}
