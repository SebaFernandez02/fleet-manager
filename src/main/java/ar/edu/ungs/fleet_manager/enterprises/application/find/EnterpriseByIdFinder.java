package ar.edu.ungs.fleet_manager.enterprises.application.find;

import ar.edu.ungs.fleet_manager.enterprises.application.EnterpriseResponse;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseRepository;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public final class EnterpriseByIdFinder {
    private final EnterpriseRepository repository;

    public EnterpriseByIdFinder(EnterpriseRepository repository) {
        this.repository = repository;
    }

    public EnterpriseResponse execute(String id) {
        return this.repository.findById(new EnterpriseId(id))
                              .map(EnterpriseResponse::map)
                              .orElseThrow(() -> new NotFoundException("enterprise not found"));
    }
}
