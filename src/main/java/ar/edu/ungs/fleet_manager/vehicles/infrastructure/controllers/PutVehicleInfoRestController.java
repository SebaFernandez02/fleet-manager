package ar.edu.ungs.fleet_manager.vehicles.infrastructure.controllers;


import ar.edu.ungs.fleet_manager.vehicles.application.VehicleInfoRequest;
import ar.edu.ungs.fleet_manager.vehicles.application.update.VehicleInfoUpdater;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PutVehicleInfoRestController {
    private final VehicleInfoUpdater vehicleInfoUpdater;

    public PutVehicleInfoRestController(VehicleInfoUpdater vehicleInfoUpdater) {
        this.vehicleInfoUpdater = vehicleInfoUpdater;
    }

    @PutMapping("/api/vehicles/{id}/info")
    public ResponseEntity<?> handle(@RequestBody VehicleInfoRequest request, @PathVariable String id) {

        this.vehicleInfoUpdater.execute(id, request);

        return ResponseEntity.ok().build();
    }
}
