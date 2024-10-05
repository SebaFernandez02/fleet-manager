package ar.edu.ungs.fleet_manager.reserves.domain.services;

import ar.edu.ungs.fleet_manager.reserves.domain.Trip;
import ar.edu.ungs.fleet_manager.vehicles.domain.Coordinates;

public interface TripCalculator {
    Trip execute(Coordinates origin, Coordinates destination);
}
