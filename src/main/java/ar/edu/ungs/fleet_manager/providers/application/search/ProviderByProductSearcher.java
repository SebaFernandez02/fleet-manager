package ar.edu.ungs.fleet_manager.providers.application.search;


import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.providers.application.ProviderResponse;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ProviderByProductSearcher {
    private final ProviderRepository repository;

    public ProviderByProductSearcher(ProviderRepository repository) {
        this.repository = repository;
    }

    public List<ProviderResponse> execute(String id) {
        return this.repository.searchProvidersByProduct(new ProductId(id))
                .stream()
                .map(ProviderResponse::map)
                .toList();
    }
}
