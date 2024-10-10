package ar.edu.ungs.fleet_manager.alerts.domain.services;

import ar.edu.ungs.fleet_manager.alerts.domain.Alert;
import ar.edu.ungs.fleet_manager.alerts.domain.AlertRepository;
import ar.edu.ungs.fleet_manager.alerts.domain.AlertStrategy;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import org.springframework.stereotype.Component;

@Component
public final class AlertCreator {
    private final AlertRepository repository;

    public AlertCreator(AlertRepository repository) {
        this.repository = repository;
    }

    public void execute(AlertStrategy strategy, VehicleId vehicleId) {
        Alert alert = Alert.factory(strategy, vehicleId.value());

        this.repository.save(alert);
    }
}
