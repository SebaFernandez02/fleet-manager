package ar.edu.ungs.fleet_manager.shared.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

import java.util.UUID;

public class Identifier {
    private final UUID value;

    public Identifier(String value) {
        try {
            this.value = UUID.fromString(value);
        }catch(Exception e){
            throw new InvalidParameterException("ID Nulo en Identificador");
        }
    }

    public String value() {
        return value.toString();
    }
}
