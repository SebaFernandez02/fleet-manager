package ar.edu.ungs.fleet_manager.reserves.domain;

import ar.edu.ungs.fleet_manager.vehicles.domain.Coordinates;

public record Trip(Coordinates from, Coordinates to) {
}