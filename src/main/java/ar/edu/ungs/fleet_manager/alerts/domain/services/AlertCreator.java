package ar.edu.ungs.fleet_manager.alerts.domain.services;

import ar.edu.ungs.fleet_manager.alerts.application.search.AlertsSearcher;
import ar.edu.ungs.fleet_manager.alerts.domain.Alert;
import ar.edu.ungs.fleet_manager.alerts.domain.AlertNotifier;
import ar.edu.ungs.fleet_manager.alerts.domain.AlertRepository;
import ar.edu.ungs.fleet_manager.alerts.domain.AlertStrategy;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import org.springframework.stereotype.Component;

@Component
public final class AlertCreator {
    private final AlertRepository repository;
    private final AlertsSearcher searcher;
    private final AlertNotifier notifier;

    public AlertCreator(AlertRepository repository, AlertsSearcher searcher, AlertNotifier notifier) {
        this.repository = repository;
        this.searcher = searcher;
        this.notifier = notifier;
    }

    public void execute(AlertStrategy strategy,
                        String vehicleId,
                        String enterpriseId) {
        Alert alert = Alert.factory(strategy, vehicleId, enterpriseId);

        if (!alreadyContainsActiveAlert(alert)) {
            this.repository.save(alert);
            this.notifier.execute(alert);
        }
    }

    private boolean alreadyContainsActiveAlert(Alert alert) {
        return this.searcher.execute(alert.enterpriseId().value())
                            .stream().filter(x -> !x.acknowledge())
                            .filter(x -> x.description().equals(alert.description().value()))
                            .anyMatch(x -> x.title().equals(alert.title().value()));
    }
}
