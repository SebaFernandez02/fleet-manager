package ar.edu.ungs.fleet_manager.providers.application.create;


import ar.edu.ungs.fleet_manager.providers.application.ProviderRequest;
import ar.edu.ungs.fleet_manager.providers.domain.Provider;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderId;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderName;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderRepository;
import ar.edu.ungs.fleet_manager.providers.domain.services.ProviderFinder;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public final class ProviderCreator {
    private final ProviderRepository repository;
    private final ProviderFinder finder;

    public ProviderCreator(ProviderRepository repository,
                           ProviderFinder finder) {
        this.repository = repository;
        this.finder = finder;
    }

    public void execute(ProviderRequest request) {
        this.ensureProviderNotExists(request.id());

        Provider provider = Provider.create(request.name(),
                request.email(),
                request.cuit(),
                request.phoneNumber(),
                request.address());

        this.repository.save(provider);
    }

    private void ensureProviderNotExists(String id) {
        try {
            this.finder.execute(new ProviderId(id));

            throw new InvalidParameterException(String.format("the provider %s already exists", id));
        } catch (NotFoundException ignored) {}
    }
}
