package ar.edu.ungs.fleet_manager.vehicles.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.vehicles.application.CoordinatesRequest;
import ar.edu.ungs.fleet_manager.vehicles.application.update.VehicleCoordinatesUpdater;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PutVehicleRestController {
    private final VehicleCoordinatesUpdater vehicleCoordinatesUpdater;

    public PutVehicleRestController(VehicleCoordinatesUpdater vehicleCoordinatesUpdater) {
        this.vehicleCoordinatesUpdater = vehicleCoordinatesUpdater;
    }

    @PutMapping("/api/vehicles/{id}/coordinates")
    public ResponseEntity<?> handle(@RequestBody CoordinatesRequest request, @PathVariable String id) {
        this.vehicleCoordinatesUpdater.execute(id, request);

        return ResponseEntity.ok().build();
    }
}
