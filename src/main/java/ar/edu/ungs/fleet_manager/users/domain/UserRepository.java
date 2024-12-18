package ar.edu.ungs.fleet_manager.users.domain;

import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);

    Optional<User> findById(UserId id);

    Optional<User> findByUsername(Username username);

    List<User> searchAll(EnterpriseId enterpriseId);
}
