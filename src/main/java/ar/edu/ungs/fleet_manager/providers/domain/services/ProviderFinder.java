package ar.edu.ungs.fleet_manager.providers.domain.services;

import ar.edu.ungs.fleet_manager.providers.domain.Provider;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderId;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderName;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderRepository;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleRepository;
import org.springframework.stereotype.Component;

@Component
public final class ProviderFinder {
    private final ProviderRepository repository;

    public ProviderFinder(ProviderRepository repository) {
        this.repository = repository;
    }

    public Provider execute(ProviderId id) {
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException(String.format("provider %s not found", id.value())));
    }
}
