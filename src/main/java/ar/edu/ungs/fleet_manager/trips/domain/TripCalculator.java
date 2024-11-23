package ar.edu.ungs.fleet_manager.trips.domain;

import ar.edu.ungs.fleet_manager.vehicles.domain.Coordinates;

public interface TripCalculator {
    Trip execute(Coordinates origin, Coordinates destination, String enterpriseId);
}
