package ar.edu.ungs.fleet_manager.vehicles.infrastructure.controllers;


import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.VehicleUnavailabler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DeleteVehicleRestController {
    private final VehicleUnavailabler vehicleUnavailabler;

    public DeleteVehicleRestController(VehicleUnavailabler vehicleUnavailabler) {
        this.vehicleUnavailabler = vehicleUnavailabler;
    }

    @DeleteMapping("/api/vehicles/{id}")
    public ResponseEntity<?> handle(@PathVariable String id) {
        this.vehicleUnavailabler.execute(new VehicleId(id));

        return ResponseEntity.ok().build();
    }
}
