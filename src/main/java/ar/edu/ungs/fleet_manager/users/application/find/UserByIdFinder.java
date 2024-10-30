package ar.edu.ungs.fleet_manager.users.application.find;

import ar.edu.ungs.fleet_manager.enterprises.domain.Enterprise;
import ar.edu.ungs.fleet_manager.enterprises.domain.services.EnterpriseFinder;
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
    private final EnterpriseFinder enterpriseFinder;

    public UserByIdFinder(UserFinder userFinder,
                          PermissionsFinder permissionsFinder,
                          EnterpriseFinder enterpriseFinder) {
        this.userFinder = userFinder;
        this.permissionsFinder = permissionsFinder;
        this.enterpriseFinder = enterpriseFinder;
    }

    public UserResponse execute(String id) {
        User user = this.userFinder.execute(new UserId(id));
        Permissions permissions = this.permissionsFinder.execute(user.id());
        Enterprise enterprise = user.enterpriseId().map(x -> this.enterpriseFinder.execute(x.value())).orElse(null);

        return UserResponse.map(user, permissions, enterprise);
    }
}
