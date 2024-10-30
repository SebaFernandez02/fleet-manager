package ar.edu.ungs.fleet_manager.users.application;

public record UserRequest(String username,
                          String password,
                          String fullName,
                          String role,
                          String enterpriseId) {
}
