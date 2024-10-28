package ar.edu.ungs.fleet_manager.users.application.search;

import ar.edu.ungs.fleet_manager.enterprises.domain.Enterprise;
import ar.edu.ungs.fleet_manager.enterprises.domain.services.EnterpriseFinder;
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
    private final EnterpriseFinder enterpriseFinder;

    public UserAllSearcher(UserRepository repository,
                           PermissionsFinder permissionsFinder,
                           EnterpriseFinder enterpriseFinder) {
        this.repository = repository;
        this.permissionsFinder = permissionsFinder;
        this.enterpriseFinder = enterpriseFinder;
    }

    public List<UserResponse> execute() {
        return this.repository.searchAll()
                              .stream()
                              .map(this::apply)
                              .toList();
    }

    private UserResponse apply(User user) {
        Permissions permissions = permissionsFinder.execute(user.roles());
        Enterprise enterprise = user.enterpriseId().map(x -> enterpriseFinder.execute(x.value())).orElse(null);

        return UserResponse.map(user, permissions, enterprise);
    }
}
