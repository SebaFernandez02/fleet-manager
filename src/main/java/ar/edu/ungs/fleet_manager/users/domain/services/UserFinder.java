package ar.edu.ungs.fleet_manager.users.domain.services;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import ar.edu.ungs.fleet_manager.users.domain.User;
import ar.edu.ungs.fleet_manager.users.domain.UserId;
import ar.edu.ungs.fleet_manager.users.domain.UserRepository;
import ar.edu.ungs.fleet_manager.users.domain.Username;
import org.springframework.stereotype.Component;

@Component
public final class UserFinder {
    private final UserRepository repository;

    public UserFinder(UserRepository repository) {
        this.repository = repository;
    }

    public User execute(UserId id) {
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException("user not found"));
    }

    public User execute(Username username) {
        return this.repository.findByUsername(username).orElseThrow(() -> new NotFoundException("user not found"));
    }
}
