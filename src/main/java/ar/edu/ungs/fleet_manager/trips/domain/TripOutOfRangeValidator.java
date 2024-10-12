package ar.edu.ungs.fleet_manager.trips.domain;

import ar.edu.ungs.fleet_manager.vehicles.domain.Coordinates;

public interface TripOutOfRangeValidator {
    Boolean execute(Trip trip, Coordinates coordinates);
}
