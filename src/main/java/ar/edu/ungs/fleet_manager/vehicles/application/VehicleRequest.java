package ar.edu.ungs.fleet_manager.vehicles.application;

import ar.edu.ungs.fleet_manager.shared.aplication.CoordinatesRequest;

public record VehicleRequest(String id,
                             String model,
                             String brand,
                             Integer year,
                             CoordinatesRequest coordinates) {
}