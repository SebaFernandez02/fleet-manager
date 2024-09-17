package ar.edu.ungs.fleet_manager.shared.domain.exceptions;

public final class NotFoundException extends DomainException{
    private static final String CODE = "not_found";

    public NotFoundException(String message) {
        super(message, CODE);
    }
}
