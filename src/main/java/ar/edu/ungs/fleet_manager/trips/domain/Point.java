package ar.edu.ungs.fleet_manager.trips.domain;

import ar.edu.ungs.fleet_manager.vehicles.domain.Coordinates;

public record Point(Coordinates coordinates, String address) {
}
