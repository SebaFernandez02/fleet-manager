package ar.edu.ungs.fleet_manager.users.domain;

import ar.edu.ungs.fleet_manager.shared.domain.Module;

import java.util.Set;

public record Permission(Module module, Set<Operation> operations) {
}
