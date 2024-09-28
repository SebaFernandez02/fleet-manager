package ar.edu.ungs.fleet_manager.vehicles.infrastructure.controllers;


import ar.edu.ungs.fleet_manager.vehicles.application.VehicleStatusRequest;
import ar.edu.ungs.fleet_manager.vehicles.application.update.VehicleStatusUpdater;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PutVehicleStatusRestController {
    private final VehicleStatusUpdater vehicleStatusUpdater;

    public PutVehicleStatusRestController(VehicleStatusUpdater vehicleStatusUpdater) {
        this.vehicleStatusUpdater = vehicleStatusUpdater;
    }

    @PutMapping("/api/vehicles/{id}/status")
    public ResponseEntity<?> handle(@RequestBody VehicleStatusRequest request, @PathVariable String id) {
        this.vehicleStatusUpdater.execute(id, request);

        return ResponseEntity.ok().build();
    }
}
