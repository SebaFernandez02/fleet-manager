package ar.edu.ungs.fleet_manager.reserves.application;

import ar.edu.ungs.fleet_manager.reserves.domain.Reserve;

import java.time.LocalDateTime;

public record ReserveResponse(String id,
                              String vehicleId,
                              String userId,
                              String status,
                              ReserveTripResponse trip,
                              LocalDateTime dateCreated,
                              LocalDateTime dateUpdated) {
    public static ReserveResponse map(Reserve aggregate) {
        return new ReserveResponse(aggregate.id().value(),
                aggregate.vehicleId().value(),
                aggregate.userId().value(),
                aggregate.status().name(),
                ReserveTripResponse.map(aggregate.trip()),
                aggregate.dateCreated(),
                aggregate.dateUpdated());
    }
}
