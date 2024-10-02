package ar.edu.ungs.fleet_manager.shared.infrastructure.configs;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.DomainException;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.UnauthorizedException;
import ar.edu.ungs.fleet_manager.shared.infrastructure.exceptions.InfrastructureException;
import ar.edu.ungs.fleet_manager.shared.infrastructure.persistence.PostgresException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.util.Map;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(InfrastructureException.class)
    public ResponseEntity<?> infrastructureError(InfrastructureException error) {
        var response = Map.of("code", error.code(), "message", error.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionError(Exception error) {
        var response = Map.of("code", "internal_error", "message", error.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundError(NotFoundException error) {
        var response = Map.of("code", error.code(), "message", error.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> unauthorizedError(UnauthorizedException error) {
        var response = Map.of("code", error.code(), "message", error.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<?> invalidParameterError(InvalidParameterException error) {
        var response = Map.of("code", error.code(), "message", error.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<?> domainError(DomainException error) {
        var response = Map.of("code", error.code(), "message", error.getMessage());
        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(PostgresException.class)
    public ResponseEntity<?> sqlError(PostgresException error){
        var response = Map.of("code", error.code(), "message", error.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }

}

