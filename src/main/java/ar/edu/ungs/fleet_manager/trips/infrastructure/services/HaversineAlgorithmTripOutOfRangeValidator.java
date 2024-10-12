package ar.edu.ungs.fleet_manager.trips.infrastructure.services;

import ar.edu.ungs.fleet_manager.trips.domain.Route;
import ar.edu.ungs.fleet_manager.trips.domain.Trip;
import ar.edu.ungs.fleet_manager.trips.domain.TripOutOfRangeValidator;
import ar.edu.ungs.fleet_manager.vehicles.domain.Coordinates;
import org.springframework.stereotype.Component;

@Component
public class HaversineAlgorithmTripOutOfRangeValidator implements TripOutOfRangeValidator {
    private final static Double RANGE_IN_KM = 50.0;
    private final static Integer RATE_OF_EARTH_IN_KM = 6378;

    @Override
    public Boolean execute(Trip trip, Coordinates coordinates) {
        return trip.routes().stream().allMatch(route -> validate(route, coordinates));
    }

    private Boolean validate(Route route, Coordinates coordinates) {
        return route.steps().stream().allMatch(step -> haversine(step, coordinates));
    }

    public Boolean haversine(Coordinates step, Coordinates coordinates) {
        double latDistance = Math.toRadians(coordinates.latitude() - step.latitude());
        double lonDistance = Math.toRadians(coordinates.longitude() - step.longitude());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(step.latitude())) * Math.cos(Math.toRadians(coordinates.latitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        var kmDistance = RATE_OF_EARTH_IN_KM * c;
        return RANGE_IN_KM < kmDistance;
    }
}
