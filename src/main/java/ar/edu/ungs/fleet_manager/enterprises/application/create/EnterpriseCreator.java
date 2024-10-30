package ar.edu.ungs.fleet_manager.enterprises.application.create;

import ar.edu.ungs.fleet_manager.enterprises.application.EnterpriseRequest;
import ar.edu.ungs.fleet_manager.enterprises.domain.Enterprise;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseRepository;
import org.springframework.stereotype.Component;

@Component
public final class EnterpriseCreator {
    private final EnterpriseRepository repository;

    public EnterpriseCreator(EnterpriseRepository repository) {
        this.repository = repository;
    }

    public void execute(EnterpriseRequest request) {
        var enterprise = Enterprise.create(request.name());

        this.repository.save(enterprise);
    }
}
