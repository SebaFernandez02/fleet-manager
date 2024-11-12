package ar.edu.ungs.fleet_manager.providers.application.create;


import ar.edu.ungs.fleet_manager.providers.application.ProviderRequest;
import ar.edu.ungs.fleet_manager.providers.domain.*;
import ar.edu.ungs.fleet_manager.providers.domain.services.ProviderFinder;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public final class ProviderCreator {
    private final ProviderRepository repository;
    private final ProviderFinder finder;

    public ProviderCreator(ProviderRepository repository, ProviderFinder finder) {
        this.repository = repository;
        this.finder = finder;
    }

    public void execute(ProviderRequest request) {
        this.ensureProviderNotExists(request.cuit());

        Provider provider = Provider.create(request.name(),
                                            request.email(),
                                            request.cuit(),
                                            request.phoneNumber(),
                                            request.address(),
                                            request.enterpriseId());

        this.repository.save(provider);
    }

    private void ensureProviderNotExists(String cuit) {
        try {
            this.finder.execute(new ProviderCuit(cuit));

            throw new InvalidParameterException(String.format("the providerId %s already exists", cuit));
        } catch (NotFoundException ignored) {}
    }
}
