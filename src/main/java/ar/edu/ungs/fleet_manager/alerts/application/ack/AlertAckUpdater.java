package ar.edu.ungs.fleet_manager.alerts.application.ack;

import ar.edu.ungs.fleet_manager.alerts.domain.Alert;
import ar.edu.ungs.fleet_manager.alerts.domain.AlertId;
import ar.edu.ungs.fleet_manager.alerts.domain.AlertRepository;
import ar.edu.ungs.fleet_manager.alerts.domain.services.AlertFinder;
import org.springframework.stereotype.Component;

@Component
public final class AlertAckUpdater {
    private final AlertFinder finder;
    private final AlertRepository repository;

    public AlertAckUpdater(AlertFinder finder, AlertRepository repository) {
        this.finder = finder;
        this.repository = repository;
    }

    public void execute(String id) {
        Alert alert = this.finder.execute(new AlertId(id));

        alert.ack();

        this.repository.save(alert);
    }
}
