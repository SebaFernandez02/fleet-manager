package ar.edu.ungs.fleet_manager.users.application.find;

import ar.edu.ungs.fleet_manager.users.application.UserResponse;
import ar.edu.ungs.fleet_manager.users.domain.Permissions;
import ar.edu.ungs.fleet_manager.users.domain.User;
import ar.edu.ungs.fleet_manager.users.domain.UserId;
import ar.edu.ungs.fleet_manager.users.domain.services.PermissionsFinder;
import ar.edu.ungs.fleet_manager.users.domain.services.UserFinder;
import org.springframework.stereotype.Component;

@Component
public final class UserByIdFinder {
    private final UserFinder userFinder;
    private final PermissionsFinder permissionsFinder;

    public UserByIdFinder(UserFinder userFinder,
                          PermissionsFinder permissionsFinder) {
        this.userFinder = userFinder;
        this.permissionsFinder = permissionsFinder;
    }

    public UserResponse execute(String id) {
        User user = this.userFinder.execute(new UserId(id));
        Permissions permissions = this.permissionsFinder.execute(user.id());

        return UserResponse.map(user, permissions);
    }
}
