package ar.edu.ungs.fleet_manager.enterprises.domain;

import java.util.UUID;

public record Enterprise(EnterpriseId id, EnterpriseName name) {
    public static Enterprise create(String name) {
        EnterpriseId id = new EnterpriseId(UUID.randomUUID().toString());
        return new Enterprise(id, new EnterpriseName(name));
    }

    public static Enterprise build(String id, String name) {
        return new Enterprise(new EnterpriseId(id), new EnterpriseName(name));
    }
}
