package ar.edu.ungs.fleet_manager.alerts.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.alerts.application.AlertRequest;
import ar.edu.ungs.fleet_manager.alerts.domain.AlertStrategy;
import ar.edu.ungs.fleet_manager.alerts.domain.services.AlertCreator;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class PostAlertController {
    private final AlertCreator creator;

    public PostAlertController(AlertCreator creator) {
        this.creator = creator;
    }

    @PostMapping("/api/alerts")
    public ResponseEntity<?> handle(@RequestBody AlertRequest request) {
        AlertStrategy strategy = AlertStrategy.valueOf(request.strategy().toUpperCase(Locale.ROOT));
        VehicleId vehicleId = new VehicleId(request.vehicleId());

        this.creator.execute(strategy, vehicleId);

        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }
}
