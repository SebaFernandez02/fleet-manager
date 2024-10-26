package ar.edu.ungs.fleet_manager.enterprises.application;

import ar.edu.ungs.fleet_manager.enterprises.domain.Enterprise;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public record EnterpriseResponse(String id,
                                 String name,
                                 Boolean isActive,
                                 String type,
                                 List<String> modules,
                                 LocalDateTime dateCreated,
                                 LocalDateTime dateUpdated) {
    public static EnterpriseResponse map(Enterprise enterprise) {
        return new EnterpriseResponse(enterprise.id().value(),
                                      enterprise.name().value(),
                                      enterprise.isActive(),
                                      enterprise.type().name(),
                                      Arrays.stream(enterprise.type().modules()).map(Enum::name).toList(),
                                      enterprise.dateCreated(),
                                      enterprise.dateUpdated());
    }
}
