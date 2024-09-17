package ar.edu.ungs.fleet_manager.shared.domain.exceptions;

public abstract class DomainException extends RuntimeException{
    private final String code;

    public DomainException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String code() {
        return code;
    }
}
