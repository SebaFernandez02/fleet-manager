package ar.edu.ungs.fleet_manager.alerts.domain.services;

import ar.edu.ungs.fleet_manager.alerts.domain.Alert;
import ar.edu.ungs.fleet_manager.alerts.domain.AlertId;
import ar.edu.ungs.fleet_manager.alerts.domain.AlertRepository;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public final class AlertFinder {
    private final AlertRepository repository;

    public AlertFinder(AlertRepository repository) {
        this.repository = repository;
    }

    public Alert execute(AlertId id) {
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException("alert not found"));
    }
}
