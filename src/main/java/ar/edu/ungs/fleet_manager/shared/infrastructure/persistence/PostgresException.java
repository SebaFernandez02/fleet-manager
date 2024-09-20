package ar.edu.ungs.fleet_manager.shared.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.shared.infrastructure.exceptions.InfrastructureException;

public class PostgresException extends InfrastructureException {
    public PostgresException(String message) {
        super(message);
    }
}
