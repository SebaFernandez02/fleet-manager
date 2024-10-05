package ar.edu.ungs.fleet_manager.reserves.application;

import ar.edu.ungs.fleet_manager.shared.aplication.CoordinatesRequest;

public record ReserveRequest(String vehicleId, String userId, CoordinatesRequest destination) {
}
