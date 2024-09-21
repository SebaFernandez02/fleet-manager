package ar.edu.ungs.fleet_manager.users.domain;

import java.util.Set;

public record Permission(Module module, Set<Operation> operations) {
}
