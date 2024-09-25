package ar.edu.ungs.fleet_manager.shared.domain.exceptions;

public final class UnauthorizedException extends DomainException {
    private static final String CODE = "unauthorized";

    public UnauthorizedException(String message) {
        super(message, CODE);
    }
}
