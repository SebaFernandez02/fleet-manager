package ar.edu.ungs.fleet_manager.vehicles.application;

import ar.edu.ungs.fleet_manager.vehicles.domain.Coordinates;

public record CoordinatesResponse(Double latitude, Double longitude) {
    public static CoordinatesResponse map(Coordinates coordinates) {
        return new CoordinatesResponse(coordinates.latitude(), coordinates.longitude());
    }
}
