package ar.edu.ungs.fleet_manager.vehicles.application;

import ar.edu.ungs.fleet_manager.shared.aplication.CoordinatesResponse;
import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;

import java.time.LocalDateTime;

public record VehicleResponse(String id,
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
                              CoordinatesResponse coordinates,
                              LocalDateTime dateCreated,
                              LocalDateTime dateUpdated) {
    public static VehicleResponse map(Vehicle vehicle) {
        return new VehicleResponse(vehicle.id().value(),
                                   vehicle.status().name(),
                                   vehicle.model().value(),
                                   vehicle.brand().value(),
                                   vehicle.year().value(),
                                   vehicle.type().name(),
                                   vehicle.color().value(),
                                   vehicle.fuelType().name(),
                                   vehicle.fuelMeasurement().name(),
                                   vehicle.fuelConsumption().value(),
                                   vehicle.cantAxles().value(),
                                   vehicle.cantSeats().value(),
                                   vehicle.load().value(),
                                   vehicle.hasTrailer(),
                                   CoordinatesResponse.map(vehicle.coordinates()),
                                   vehicle.dateCreated(),
                                   vehicle.dateUpdated());
    }
}