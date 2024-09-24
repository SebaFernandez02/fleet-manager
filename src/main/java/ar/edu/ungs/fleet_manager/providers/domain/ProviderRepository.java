package ar.edu.ungs.fleet_manager.providers.domain;

import java.util.List;
import java.util.Optional;

public interface ProviderRepository {
    void save(Provider provider);

    Optional<Provider> findById(ProviderId id);

    List<Provider> searchAll();
}
