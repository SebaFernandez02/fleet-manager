package ar.edu.ungs.fleet_manager.trips.infrastructure.services;

import ar.edu.ungs.fleet_manager.configs.domain.ApiKeyType;
import ar.edu.ungs.fleet_manager.configs.domain.services.ApiKeyFinder;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import ar.edu.ungs.fleet_manager.trips.domain.Point;
import ar.edu.ungs.fleet_manager.trips.domain.Route;
import ar.edu.ungs.fleet_manager.trips.domain.Trip;
import ar.edu.ungs.fleet_manager.trips.domain.TripCalculator;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;
import ar.edu.ungs.fleet_manager.shared.infrastructure.exceptions.InfrastructureException;
import ar.edu.ungs.fleet_manager.vehicles.domain.Coordinates;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ZeroResultsException;
import com.google.maps.model.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class GoogleTripCalculator implements TripCalculator {

    private final ApiKeyFinder keyFinder;

    public GoogleTripCalculator(ApiKeyFinder keyFinder) {
        this.keyFinder = keyFinder;
    }


    public Trip execute(Coordinates from, Coordinates to, String enterpriseId) {

        GeoApiContext context = new GeoApiContext.Builder()
                                                   .apiKey(keyFinder
                                                           .execute(ApiKeyType.GOOGLE_DIRECTIONS_KEY,new EnterpriseId(enterpriseId)).key())
                                                           .build();

       // GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyALC8T9CUeF7wQTIpSRFoGOVFlOlf7-OwM").build();

        LatLng origin = new LatLng(from.latitude(), from.longitude());
        LatLng destination = new LatLng(to.latitude(), to.longitude());

        try {
            DirectionsResult result = DirectionsApi.newRequest(context)
                                                   .origin(origin)
                                                   .destination(destination)
                                                   .mode(TravelMode.DRIVING)
                                                   .await();

            List<Route> routes = Arrays.stream(result.routes).map(this::map).toList();

            return new Trip(new Point(from, result.routes[0].legs[0].startAddress),
                            new Point(to, result.routes[0].legs[0].endAddress),
                            routes);
        } catch (ZeroResultsException error) {
          throw new InvalidParameterException("can not calculate trip");
        } catch (Exception error) {
            throw new InfrastructureException(error.getMessage());
        }
    }

    private Route map(DirectionsRoute route) {
        DirectionsLeg leg = route.legs[0];
        return new Route(leg.distance.humanReadable, leg.duration.humanReadable, Arrays.stream(leg.steps).map(this::map).toList());
    }

    private Coordinates map(DirectionsStep s) {
        return new Coordinates(s.endLocation.lat, s.endLocation.lng);
    }
}
