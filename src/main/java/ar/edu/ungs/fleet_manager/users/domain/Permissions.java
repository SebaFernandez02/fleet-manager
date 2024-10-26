package ar.edu.ungs.fleet_manager.users.domain;

import ar.edu.ungs.fleet_manager.shared.domain.Module;

import java.util.Set;

public record Permissions(Set<Permission> values) {
    public Boolean contains(Module module, Operation operation) {
        return values.stream()
                     .filter(x -> x.module().equals(module))
                     .map(Permission::operations)
                     .anyMatch(x -> x.contains(operation));
    }
}
