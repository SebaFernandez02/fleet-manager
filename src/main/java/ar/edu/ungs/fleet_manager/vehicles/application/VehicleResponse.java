package ar.edu.ungs.fleet_manager.vehicles.application;

import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;

import java.time.LocalDateTime;

public record VehicleResponse(String id,
                              String status,
                              String model,
                              String brand,
                              Integer year,
                              CoordinatesResponse coordinates,
                              LocalDateTime dateCreated,
                              LocalDateTime dateUpdated) {
    public static VehicleResponse map(Vehicle vehicle) {
        return new VehicleResponse(vehicle.id().value(),
                                   vehicle.status().name(),
                                   vehicle.model().value(),
                                   vehicle.brand().value(),
                                   vehicle.year().value(),
                                   CoordinatesResponse.map(vehicle.coordinates()),
                                   vehicle.dateCreated(),
                                   vehicle.dateUpdated());
    }
}