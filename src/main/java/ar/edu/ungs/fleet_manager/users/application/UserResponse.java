package ar.edu.ungs.fleet_manager.users.application;

import ar.edu.ungs.fleet_manager.users.domain.Permissions;
import ar.edu.ungs.fleet_manager.users.domain.Role;
import ar.edu.ungs.fleet_manager.users.domain.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record UserResponse(String id,
                           String username,
                           String fullName,
                           List<String> roles,
                           List<PermissionResponse> permissions,
                           LocalDateTime dateCreated,
                           LocalDateTime dateUpdated) {
    public static UserResponse map(User user) {
        return new UserResponse(user.id().value(),
                user.username().value(),
                user.fullName().value(),
                user.roles().stream().map(Role::name).toList(),
                Collections.emptyList(),
                user.dateCreated(),
                user.dateUpdated());
    }

    public static UserResponse map(User user, Permissions permissions) {
        return new UserResponse(user.id().value(),
                user.username().value(),
                user.fullName().value(),
                user.roles().stream().map(Role::name).toList(),
                permissions.values().stream().map(PermissionResponse::map).toList(),
                user.dateCreated(),
                user.dateUpdated());
    }
}
