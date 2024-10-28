package ar.edu.ungs.fleet_manager.enterprises.application;

import ar.edu.ungs.fleet_manager.enterprises.domain.Enterprise;

public record EnterpriseResponse(String id, String name) {
    public static EnterpriseResponse map(Enterprise enterprise) {
        return new EnterpriseResponse(enterprise.id().value(), enterprise.name().value());
    }
}
