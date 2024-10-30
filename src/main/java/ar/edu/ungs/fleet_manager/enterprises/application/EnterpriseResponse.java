package ar.edu.ungs.fleet_manager.enterprises.application;

import ar.edu.ungs.fleet_manager.enterprises.domain.Enterprise;
import ar.edu.ungs.fleet_manager.shared.domain.Module;

import java.util.List;
import java.util.stream.Collectors;

public record EnterpriseResponse(String id, String name, List<String> modules) {
    public static EnterpriseResponse map(Enterprise enterprise) {
        return new EnterpriseResponse(enterprise.id().value(),
                                     enterprise.name().value(),
                                     enterprise.modules().stream().map(Module::name).collect(Collectors.toList()));
    }
}
