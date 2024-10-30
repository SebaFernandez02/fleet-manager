package ar.edu.ungs.fleet_manager.users.domain;

import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public final class User {
    private final UserId id;
    private final Set<Role> roles;
    private final Optional<EnterpriseId> enterpriseId;
    private final Username username;
    private Password password;
    private final FullName fullName;
    private final LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    public User(UserId id,
                Set<Role> roles, Optional<EnterpriseId> enterpriseId,
                Username username,
                Password password,
                FullName fullName,
                LocalDateTime dateCreated,
                LocalDateTime dateUpdated) {
        this.id = id;
        this.roles = roles;
        this.enterpriseId = enterpriseId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    public static User create(String username,
                              String password,
                              String fullName,
                              String enterpriseId) {
        return build(UUID.randomUUID().toString(),
                     Collections.emptyList(),
                     username,
                     password,
                     fullName,
                     enterpriseId,
                     LocalDateTime.now(),
                     LocalDateTime.now());
    }

    public static User build(String id,
                             List<String> roles,
                             String username,
                             String password,
                             String fullName,
                             String enterpriseId,
                             LocalDateTime dateCreated,
                             LocalDateTime dateUpdated) {
        return new User(new UserId(id),
                        roles.stream().map(Role::parse).collect(Collectors.toSet()),
                        Optional.ofNullable(enterpriseId).flatMap(value -> Optional.of(new EnterpriseId(value))),
                        new Username(username),
                        new Password(password),
                        new FullName(fullName),
                        dateCreated, dateUpdated);
    }

    public UserId id() {
        return id;
    }

    public Set<Role> roles() {
        return roles;
    }

    public void add(Role role) {
        this.roles.add(role);

        this.dateUpdated = LocalDateTime.now();
    }

    public Optional<EnterpriseId> enterpriseId() {
        return enterpriseId;
    }

    public Username username() {
        return username;
    }

    public Password password() {
        return password;
    }

    public void change(Password password) {
        this.password = password;

        this.dateUpdated = LocalDateTime.now();
    }

    public boolean isValidPassword(Password password) {
        return this.password.equals(password);
    }

    public FullName fullName() {
        return fullName;
    }

    public LocalDateTime dateCreated() {
        return dateCreated;
    }

    public LocalDateTime dateUpdated() {
        return dateUpdated;
    }

    public boolean isCustomer() {
        return roles().contains(Role.CUSTOMER);
    }

    public boolean isOperator() {
        return roles().contains(Role.OPERATOR);
    }

    public boolean isDeveloper() {
        return roles().contains(Role.DEVELOPER);
    }

    public boolean isAdmin() {
        return roles().contains(Role.ADMIN);
    }
}
