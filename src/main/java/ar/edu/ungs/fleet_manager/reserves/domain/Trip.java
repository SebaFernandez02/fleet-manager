package ar.edu.ungs.fleet_manager.reserves.domain;

import java.util.List;

public record Trip(Point origin, Point destination, List<Route> routes) {
}
