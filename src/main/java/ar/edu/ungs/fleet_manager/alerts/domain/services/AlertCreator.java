package ar.edu.ungs.fleet_manager.alerts.domain.services;

import ar.edu.ungs.fleet_manager.alerts.application.search.AlertsSearcher;
import ar.edu.ungs.fleet_manager.alerts.domain.Alert;
import ar.edu.ungs.fleet_manager.alerts.domain.AlertRepository;
import ar.edu.ungs.fleet_manager.alerts.domain.AlertStrategy;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import org.springframework.stereotype.Component;

@Component
public final class AlertCreator {
    private final AlertRepository repository;
    private final AlertsSearcher searcher;

    public AlertCreator(AlertRepository repository, AlertsSearcher searcher) {
        this.repository = repository;
        this.searcher = searcher;
    }

    public void execute(AlertStrategy strategy, VehicleId vehicleId) {
        Alert alert = Alert.factory(strategy, vehicleId.value());

        if (!alreadyContainsActiveAlert(alert)) {
            this.repository.save(alert);
        }
    }

    private boolean alreadyContainsActiveAlert(Alert alert) {
        return this.searcher.execute().stream().filter(x -> !x.acknowledge())
                .filter(x -> x.description().equals(alert.description().value()))
                .anyMatch(x -> x.title().equals(alert.title().value()));
    }
}
