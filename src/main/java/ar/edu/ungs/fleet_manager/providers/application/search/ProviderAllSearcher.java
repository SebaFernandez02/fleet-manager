package ar.edu.ungs.fleet_manager.providers.application.search;


import ar.edu.ungs.fleet_manager.providers.application.ProviderResponse;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ProviderAllSearcher {
    private final ProviderRepository repository;

    public ProviderAllSearcher(ProviderRepository repository) {
        this.repository = repository;
    }

    public List<ProviderResponse> execute() {
        return this.repository.searchAll()
                .stream()
                .map(ProviderResponse::map)
                .toList();
    }
}
