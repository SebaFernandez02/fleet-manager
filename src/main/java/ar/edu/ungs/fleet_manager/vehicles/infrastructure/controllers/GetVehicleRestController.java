package ar.edu.ungs.fleet_manager.vehicles.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.vehicles.application.VehicleResponse;
import ar.edu.ungs.fleet_manager.vehicles.application.find.VehicleByIdFinder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetVehicleRestController {
    private final VehicleByIdFinder finder;

    public GetVehicleRestController(VehicleByIdFinder finder) {
        this.finder = finder;
    }

    @GetMapping("/api/vehicles/{id}")
    public ResponseEntity<VehicleResponse> handle(@PathVariable String id) {
        VehicleResponse result = this.finder.execute(id);

        return ResponseEntity.ok(result);
    }
}
