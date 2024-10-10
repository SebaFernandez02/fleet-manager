package ar.edu.ungs.fleet_manager.alerts.application.find;

import ar.edu.ungs.fleet_manager.alerts.application.AlertResponse;
import ar.edu.ungs.fleet_manager.alerts.domain.Alert;
import ar.edu.ungs.fleet_manager.alerts.domain.AlertId;
import ar.edu.ungs.fleet_manager.alerts.domain.services.AlertFinder;
import org.springframework.stereotype.Component;

@Component
public final class AlertByIdFinder {
    private final AlertFinder finder;

    public AlertByIdFinder(AlertFinder finder) {
        this.finder = finder;
    }

    public AlertResponse execute(String id) {
        Alert alert = this.finder.execute(new AlertId(id));

        return AlertResponse.map(alert);
    }
}
