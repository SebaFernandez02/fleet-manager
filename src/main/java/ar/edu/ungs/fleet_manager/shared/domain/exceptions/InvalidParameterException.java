package ar.edu.ungs.fleet_manager.shared.domain.exceptions;

public final class InvalidParameterException extends DomainException {
    private static final String CODE = "invalid_parameter";

    public InvalidParameterException(String message) {
        super(message, CODE);
    }
}
