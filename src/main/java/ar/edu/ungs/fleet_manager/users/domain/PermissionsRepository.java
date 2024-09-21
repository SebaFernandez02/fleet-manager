package ar.edu.ungs.fleet_manager.users.domain;

public interface PermissionsRepository {
    Permissions findByRoles(Role... roles);
}
