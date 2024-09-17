package ar.edu.ungs.fleet_manager.shared.infrastructure.exceptions;

public abstract class InfrastructureException extends RuntimeException{
    private final String code;

    public InfrastructureException(String message) {
        super(message);
        this.code = "internal_error";
    }

    public String code() {
        return code;
    }
}
