package ar.edu.ungs.fleet_manager.users.application.auth;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.UnauthorizedException;
import ar.edu.ungs.fleet_manager.users.application.UserRequest;
import ar.edu.ungs.fleet_manager.users.application.UserResponse;
import ar.edu.ungs.fleet_manager.users.domain.Password;
import ar.edu.ungs.fleet_manager.users.domain.Permissions;
import ar.edu.ungs.fleet_manager.users.domain.User;
import ar.edu.ungs.fleet_manager.users.domain.Username;
import ar.edu.ungs.fleet_manager.users.domain.services.PermissionsFinder;
import ar.edu.ungs.fleet_manager.users.domain.services.UserFinder;
import org.springframework.stereotype.Component;

@Component
public final class UserAuthenticator {
    private final UserFinder userFinder;
    private final PermissionsFinder permissionsFinder;

    public UserAuthenticator(UserFinder userFinder,
                             PermissionsFinder permissionsFinder) {
        this.userFinder = userFinder;
        this.permissionsFinder = permissionsFinder;
    }

    public UserResponse execute(UserRequest request) {
        Username username = new Username(request.username());
        Password password = new Password(request.password());

        User user = this.userFinder.execute(username);
        Permissions permissions = this.permissionsFinder.execute(user.roles());

        ensurePasswordValid(user, password);

        return UserResponse.map(user, permissions);
    }

    private void ensurePasswordValid(User user, Password password) {
        if (!user.isValidPassword(password)) {
            throw new UnauthorizedException("username or password invalid");
        }
    }
}
