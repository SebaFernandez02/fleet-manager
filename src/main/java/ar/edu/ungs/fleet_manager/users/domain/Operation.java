package ar.edu.ungs.fleet_manager.users.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

import java.util.Locale;

public enum Operation {
    POST,
    PUT,
    DELETE,
    GET;

    public static Operation parse(String operation) {
        try {
            return valueOf(operation.toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw new InvalidParameterException("operation invalid");
        }
    }
}
