package ar.edu.ungs.fleet_manager.users.application.search;

import ar.edu.ungs.fleet_manager.users.application.UserResponse;
import ar.edu.ungs.fleet_manager.users.domain.Permissions;
import ar.edu.ungs.fleet_manager.users.domain.User;
import ar.edu.ungs.fleet_manager.users.domain.UserRepository;
import ar.edu.ungs.fleet_manager.users.domain.services.PermissionsFinder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class UserAllSearcher {
    private final UserRepository repository;
    private final PermissionsFinder permissionsFinder;

    public UserAllSearcher(UserRepository repository,
                           PermissionsFinder permissionsFinder) {
        this.repository = repository;
        this.permissionsFinder = permissionsFinder;
    }

    public List<UserResponse> execute() {
        return this.repository.searchAll()
                              .stream()
                              .map(this::apply)
                              .toList();
    }

    private UserResponse apply(User user) {
        Permissions permissions = permissionsFinder.execute(user.roles());

        return UserResponse.map(user, permissions);
    }
}
