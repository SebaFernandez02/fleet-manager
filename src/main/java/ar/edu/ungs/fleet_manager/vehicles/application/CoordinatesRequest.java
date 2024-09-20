package ar.edu.ungs.fleet_manager.vehicles.application;

import ar.edu.ungs.fleet_manager.vehicles.domain.Coordinates;

public record CoordinatesRequest(Double latitude, Double longitude) {
    public static CoordinatesRequest map(Coordinates coordinates) {
        return new CoordinatesRequest(coordinates.latitude(), coordinates.longitude());
    }
}
