package ar.edu.ungs.fleet_manager.reserves.domain;

import ar.edu.ungs.fleet_manager.shared.domain.Identifier;

import java.util.UUID;

public final class ReserveId extends Identifier {
    public ReserveId(String value) {
        super(value);
    }

    public static ReserveId create() {
        return new ReserveId(UUID.randomUUID().toString());
    }
}
