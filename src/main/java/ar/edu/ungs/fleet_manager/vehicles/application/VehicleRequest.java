package ar.edu.ungs.fleet_manager.vehicles.application;

public record VehicleRequest(String id,
                             String model,
                             String brand,
                             Integer year,
                             CoordinatesRequest coordinates) {
}