package ar.edu.ungs.fleet_manager.users.application;

import ar.edu.ungs.fleet_manager.enterprises.application.EnterpriseResponse;
import ar.edu.ungs.fleet_manager.enterprises.domain.Enterprise;
import ar.edu.ungs.fleet_manager.users.domain.Permission;
import ar.edu.ungs.fleet_manager.users.domain.Permissions;
import ar.edu.ungs.fleet_manager.users.domain.Role;
import ar.edu.ungs.fleet_manager.users.domain.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record UserResponse(String id,
                           String username,
                           String fullName,
                           List<String> roles,
                           List<PermissionResponse> permissions,
                           EnterpriseResponse enterprise,
                           LocalDateTime dateCreated,
                           LocalDateTime dateUpdated) {
    public static UserResponse map(User user) {
        return new UserResponse(user.id().value(),
                user.username().value(),
                user.fullName().value(),
                user.roles().stream().map(Role::name).toList(),
                Collections.emptyList(),
                null,
                user.dateCreated(),
                user.dateUpdated());
    }

    public static UserResponse map(User user, Permissions permissions, Enterprise enterprise) {
        return new UserResponse(user.id().value(),
                user.username().value(),
                user.fullName().value(),
                user.roles().stream().map(Role::name).toList(),
                permissions.values().stream().filter(permission -> isValid(user, permission,  enterprise)).map(PermissionResponse::map).toList(),
                Optional.ofNullable(enterprise).map(EnterpriseResponse::map).orElse(null),
                user.dateCreated(),
                user.dateUpdated());
    }

    private static boolean isValid(User user, Permission permission, Enterprise enterprise) {
        if (user.enterpriseId().isEmpty()) {
            return user.isDeveloper() || user.isAdmin();
        }

        return enterprise.modules().contains(permission.module());
    }
}
