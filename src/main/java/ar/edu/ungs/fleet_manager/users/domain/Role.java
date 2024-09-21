package ar.edu.ungs.fleet_manager.users.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

import java.util.Locale;

public enum Role {
    MANAGER,
    SUPERVISOR,
    ADMIN,
    OPERATOR,
    CUSTOMER;

    public static Role parse(String role) {
        try {
            return valueOf(role.toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw new InvalidParameterException(String.format("the role %s is invalid", role));
        }
    }
}
