package ar.edu.ungs.fleet_manager.vehicles.application;

import ar.edu.ungs.fleet_manager.shared.aplication.CoordinatesRequest;

public record VehicleRequest(String id,
                             String status,
                             String model,
                             String brand,
                             Integer year,
                             String type,
                             String color,
                             String fuelType,
                             String fuelMeasurement,
                             Integer fuelConsumption,
                             Integer cantAxles,
                             Integer cantSeats,
                             Integer load,
                             boolean hasTrailer,
                             String enterpriseId,
                             CoordinatesRequest coordinates) {
}