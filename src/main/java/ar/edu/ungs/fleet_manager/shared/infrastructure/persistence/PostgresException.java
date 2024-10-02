package ar.edu.ungs.fleet_manager.shared.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.shared.infrastructure.exceptions.InfrastructureException;
import org.springframework.core.NestedRuntimeException;

import java.sql.SQLException;

public class PostgresException extends InfrastructureException {
    private static final String CODE = "SQL Error";

    public PostgresException(String message) { super(message);}


    public PostgresException(SQLException sqlException) {
        super(buildDetailedMessage(sqlException));
    }

    public PostgresException(NestedRuntimeException error){
        super(buildDetailedMessage(error));
    }

    private static String buildDetailedMessage(SQLException sqlException) {
        return String.format("SQL Error Code: %d, SQL State: %s, Message: %s",
                sqlException.getErrorCode(),
                sqlException.getSQLState(),
                sqlException.getMessage());
    }

    private static String buildDetailedMessage(NestedRuntimeException error) {
        Throwable cause = error.getCause();

        if (cause instanceof SQLException sqlException) {

            System.err.println("SQL Error Code: " + sqlException.getErrorCode());
            System.err.println("SQL State: " + sqlException.getSQLState());
            System.err.println("Error Message: " + sqlException.getMessage());

            return String.format("SQL Error Code: %d  " +
                            "SQL State: %s " +
                            "Message: %s",

                    sqlException.getErrorCode(),
                    sqlException.getSQLState(),
                    sqlException.getMessage());
        }
        else{
            throw error;
        }
    }
}
