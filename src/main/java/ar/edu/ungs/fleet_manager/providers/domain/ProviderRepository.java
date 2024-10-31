package ar.edu.ungs.fleet_manager.providers.domain;

import ar.edu.ungs.fleet_manager.products.domain.ProductId;

import java.util.List;
import java.util.Optional;

public interface ProviderRepository {
    void save(Provider provider);

    Optional<Provider> findById(ProviderId id);

    Optional<Provider> findByCuit(ProviderCuit cuit);

    List<Provider> searchProvidersByProduct(ProductId id);

    List<Provider> searchAll();
}
