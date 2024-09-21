package ar.edu.ungs.fleet_manager.users.application;

import ar.edu.ungs.fleet_manager.users.domain.Operation;
import ar.edu.ungs.fleet_manager.users.domain.Permission;

import java.util.Set;
import java.util.stream.Collectors;

public record PermissionResponse(String module, Set<String> operations) {
    public static PermissionResponse map(Permission permission) {
        return new PermissionResponse(permission.module().name(), permission.operations().stream().map(Operation::name).collect(Collectors.toSet()));
    }
}
