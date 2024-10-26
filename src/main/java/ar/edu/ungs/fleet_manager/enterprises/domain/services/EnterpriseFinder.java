package ar.edu.ungs.fleet_manager.enterprises.domain.services;

import ar.edu.ungs.fleet_manager.enterprises.domain.Enterprise;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseRepository;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public final class EnterpriseFinder {
    private final EnterpriseRepository repository;

    public EnterpriseFinder(EnterpriseRepository repository) {
        this.repository = repository;
    }

    public Enterprise execute(String id) {
        return this.repository.findById(new EnterpriseId(id))
                              .orElseThrow(() -> new NotFoundException("enterprise not found"));
    }
}
