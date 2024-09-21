package ar.edu.ungs.fleet_manager.users.application.password;

import ar.edu.ungs.fleet_manager.users.application.UserRequest;
import ar.edu.ungs.fleet_manager.users.domain.Password;
import ar.edu.ungs.fleet_manager.users.domain.User;
import ar.edu.ungs.fleet_manager.users.domain.UserId;
import ar.edu.ungs.fleet_manager.users.domain.UserRepository;
import ar.edu.ungs.fleet_manager.users.domain.services.UserFinder;
import org.springframework.stereotype.Component;

@Component
public final class UserPasswordChanger {
    private final UserRepository repository;
    private final UserFinder userFinder;

    public UserPasswordChanger(UserRepository repository, UserFinder userFinder) {
        this.repository = repository;
        this.userFinder = userFinder;
    }

    public void execute(String id, UserRequest request) {
        User user = this.userFinder.execute(new UserId(id));

        user.change(new Password(request.password()));

        this.repository.save(user);
    }
}
