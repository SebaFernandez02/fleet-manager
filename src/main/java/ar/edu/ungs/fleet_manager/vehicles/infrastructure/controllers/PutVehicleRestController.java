package ar.edu.ungs.fleet_manager.vehicles.infrastructure.controllers;


import ar.edu.ungs.fleet_manager.vehicles.application.VehicleRequest;
import ar.edu.ungs.fleet_manager.vehicles.application.update.VehicleUpdater;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PutVehicleRestController {
    private final VehicleUpdater vehicleUpdater;

    public PutVehicleRestController(VehicleUpdater vehicleUpdater) {
        this.vehicleUpdater = vehicleUpdater;
    }

    @PutMapping("/api/vehicles/{id}")
    public ResponseEntity<?> handle(@PathVariable String id, @RequestBody VehicleRequest request) {
        this.vehicleUpdater.execute(id, request);

        return ResponseEntity.ok().build();
    }
}
