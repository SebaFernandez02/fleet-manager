package ar.edu.ungs.fleet_manager.enterprises.application.add;

import ar.edu.ungs.fleet_manager.enterprises.domain.Enterprise;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseRepository;
import ar.edu.ungs.fleet_manager.enterprises.domain.services.EnterpriseFinder;
import ar.edu.ungs.fleet_manager.shared.domain.Module;
import org.springframework.stereotype.Component;

@Component
public final class EnterpriseModuleAdder {
    private final EnterpriseRepository repository;
    private final EnterpriseFinder finder;

    public EnterpriseModuleAdder(EnterpriseRepository repository, EnterpriseFinder finder) {
        this.repository = repository;
        this.finder = finder;
    }

    public void execute(String id, String module) {
        Enterprise enterprise = this.finder.execute(id);

        enterprise.add(Module.parse(module));

        this.repository.save(enterprise);
    }
}
