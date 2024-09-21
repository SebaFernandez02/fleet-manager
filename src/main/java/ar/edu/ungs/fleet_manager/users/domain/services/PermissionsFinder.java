package ar.edu.ungs.fleet_manager.users.domain.services;

import ar.edu.ungs.fleet_manager.users.domain.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

@Component
public final class PermissionsFinder {
    private final UserFinder userFinder;
    private final PermissionsRepository repository;

    public PermissionsFinder(UserFinder userFinder,
                             PermissionsRepository repository) {
        this.userFinder = userFinder;
        this.repository = repository;
    }

    public Permissions execute(Set<Role> roles) {
        return this.repository.findByRoles(roles.toArray(new Role[0]));
    }

    public Permissions execute(UserId userId) {
        User user = this.userFinder.execute(userId);

        if (user.roles().isEmpty()) {
            return new Permissions(Collections.emptySet());
        }

        return this.execute(user.roles());
    }
}
