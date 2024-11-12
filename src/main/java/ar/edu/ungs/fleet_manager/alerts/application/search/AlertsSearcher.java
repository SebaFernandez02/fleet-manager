package ar.edu.ungs.fleet_manager.alerts.application.search;

import ar.edu.ungs.fleet_manager.alerts.application.AlertResponse;
import ar.edu.ungs.fleet_manager.alerts.domain.AlertRepository;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class AlertsSearcher {
    private final AlertRepository repository;

    public AlertsSearcher(AlertRepository repository) {
        this.repository = repository;
    }

    public List<AlertResponse> execute(String enterpriseId) {
        return this.repository.searchAll(new EnterpriseId(enterpriseId)).stream().map(AlertResponse::map).toList();
    }
}
