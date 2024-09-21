package ar.edu.ungs.fleet_manager.users.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

import java.util.Objects;

public record Password(String value) {
    public Password {
        if (value == null || value.isBlank()) {
            throw new InvalidParameterException("the password is blank or null");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return Objects.equals(value, password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
