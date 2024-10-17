package ar.edu.ungs.fleet_manager.alerts.application.search;

import ar.edu.ungs.fleet_manager.alerts.application.AlertResponse;
import ar.edu.ungs.fleet_manager.alerts.domain.AlertRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class AlertsSearcher {
    private final AlertRepository repository;

    public AlertsSearcher(AlertRepository repository) {
        this.repository = repository;
    }

    public List<AlertResponse> execute() {
        return this.repository.searchAll().stream().map(AlertResponse::map).toList();
    }
}
