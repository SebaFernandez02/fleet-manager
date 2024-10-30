package ar.edu.ungs.fleet_manager.enterprises.application.search;

import ar.edu.ungs.fleet_manager.enterprises.application.EnterpriseResponse;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class EnterpriseSearcher {
    private final EnterpriseRepository repository;

    public EnterpriseSearcher(EnterpriseRepository repository) {
        this.repository = repository;
    }

    public List<EnterpriseResponse> execute() {
        return this.repository.searchAll().stream().map(EnterpriseResponse::map).toList();
    }
}
