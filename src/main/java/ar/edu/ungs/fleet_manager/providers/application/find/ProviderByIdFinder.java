package ar.edu.ungs.fleet_manager.providers.application.find;


import ar.edu.ungs.fleet_manager.providers.application.ProviderResponse;
import ar.edu.ungs.fleet_manager.providers.domain.Provider;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderId;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderName;
import ar.edu.ungs.fleet_manager.providers.domain.services.ProviderFinder;
import org.springframework.stereotype.Component;

@Component
public final class ProviderByIdFinder {
    private final ProviderFinder finder;

    public ProviderByIdFinder(ProviderFinder finder) {
        this.finder = finder;
    }

    public ProviderResponse execute(String id) {
        Provider provider = this.finder.execute(new ProviderId(id));

        return ProviderResponse.map(provider);
    }
}
