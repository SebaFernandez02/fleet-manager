package ar.edu.ungs.fleet_manager.enterprises.application.update;

import ar.edu.ungs.fleet_manager.enterprises.application.EnterpriseRequest;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseRepository;
import ar.edu.ungs.fleet_manager.enterprises.domain.services.EnterpriseFinder;
import org.springframework.stereotype.Component;

@Component
public final class EnterpriseUpdater {
    private final EnterpriseRepository repository;
    private final EnterpriseFinder finder;

    public EnterpriseUpdater(EnterpriseRepository repository, EnterpriseFinder finder) {
        this.repository = repository;
        this.finder = finder;
    }

    public void execute(String id, EnterpriseRequest request) {
        var enterprise = this.finder.execute(id);

        enterprise.update(request.name(), request.type(), request.isActive());

        this.repository.save(enterprise);
    }
}
