package ar.edu.ungs.fleet_manager.users.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record Username(String value) {
    public Username {
        if (value == null || value.isBlank()) {
            throw new InvalidParameterException("the username is blank or null");
        }
    }
}
