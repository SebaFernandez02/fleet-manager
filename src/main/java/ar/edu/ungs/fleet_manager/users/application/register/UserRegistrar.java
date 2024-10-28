package ar.edu.ungs.fleet_manager.users.application.register;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import ar.edu.ungs.fleet_manager.users.application.UserRequest;
import ar.edu.ungs.fleet_manager.users.domain.User;
import ar.edu.ungs.fleet_manager.users.domain.UserRepository;
import ar.edu.ungs.fleet_manager.users.domain.Username;
import ar.edu.ungs.fleet_manager.users.domain.services.UserFinder;
import org.springframework.stereotype.Component;

@Component
public final class UserRegistrar {
    private final UserRepository repository;
    private final UserFinder userFinder;

    public UserRegistrar(UserRepository repository, UserFinder userFinder) {
        this.repository = repository;
        this.userFinder = userFinder;
    }

    public void execute(UserRequest request) {
        this.ensureUsernameNotUsed(request.username());

        User user = User.create(request.username(), request.password(), request.fullName(), request.enterpriseId());

        this.repository.save(user);
    }

    private void ensureUsernameNotUsed(String username) {
        try {
            this.userFinder.execute(new Username(username));

            throw new InvalidParameterException("username already exists");
        } catch (NotFoundException ignored) {

        }
    }
}
