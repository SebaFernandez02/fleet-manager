package ar.edu.ungs.fleet_manager.users.application.assign;

import ar.edu.ungs.fleet_manager.users.application.UserRequest;
import ar.edu.ungs.fleet_manager.users.domain.*;
import ar.edu.ungs.fleet_manager.users.domain.services.UserFinder;
import org.springframework.stereotype.Component;

@Component
public final class UserRoleAssigner {
    private final UserRepository repository;
    private final UserFinder userFinder;

    public UserRoleAssigner(UserRepository repository, UserFinder userFinder) {
        this.repository = repository;
        this.userFinder = userFinder;
    }

    public void execute(String id, UserRequest request) {
        User user = this.userFinder.execute(new UserId(id));

        user.add(Role.parse(request.role()));

        this.repository.save(user);
    }
}
