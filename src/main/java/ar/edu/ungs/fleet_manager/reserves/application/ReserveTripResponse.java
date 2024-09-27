package ar.edu.ungs.fleet_manager.reserves.application;

import ar.edu.ungs.fleet_manager.reserves.domain.Trip;
import ar.edu.ungs.fleet_manager.shared.aplication.CoordinatesResponse;

public record ReserveTripResponse(CoordinatesResponse from, CoordinatesResponse to) {
    public static ReserveTripResponse map(Trip trip) {
        return new ReserveTripResponse(CoordinatesResponse.map(trip.from()), CoordinatesResponse.map(trip.to()));
    }
}
