package ar.edu.ungs.fleet_manager.users.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record FullName(String value) {
    public FullName {
        if (value == null || value.isBlank()) {
            throw new InvalidParameterException("the password is blank or null");
        }
    }
}
