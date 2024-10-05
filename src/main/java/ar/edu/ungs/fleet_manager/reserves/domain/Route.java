package ar.edu.ungs.fleet_manager.reserves.domain;

import ar.edu.ungs.fleet_manager.vehicles.domain.Coordinates;

import java.util.List;

public record Route(String distance, String duration, List<Coordinates> steps) {
}
