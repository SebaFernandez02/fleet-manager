package ar.edu.ungs.fleet_manager.reserves.application;

import ar.edu.ungs.fleet_manager.reserves.domain.Reserve;
import ar.edu.ungs.fleet_manager.trips.domain.Trip;

import java.time.LocalDateTime;

public record ReserveResponse(String id,
                              String vehicleId,
                              String userId,
                              String status,
                              Trip trip,
                              LocalDateTime dateCreated,
                              LocalDateTime dateUpdated,
                              LocalDateTime dateReserve,
                              LocalDateTime dateFinishReserve,
                              Double fuelConsumption) {
    public static ReserveResponse map(Reserve aggregate) {
        return new ReserveResponse(aggregate.id().value(),
                aggregate.vehicleId().value(),
                aggregate.userId().value(),
                aggregate.status().name(),
                aggregate.trip(),
                aggregate.dateCreated(),
                aggregate.dateUpdated(),
                aggregate.dateReserve(),
                aggregate.dateFinishReserve(),
                aggregate.fuelConsumption());
    }
}
