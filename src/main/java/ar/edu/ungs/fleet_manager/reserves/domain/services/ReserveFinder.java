package ar.edu.ungs.fleet_manager.reserves.domain.services;

import ar.edu.ungs.fleet_manager.reserves.domain.Reserve;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveId;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveRepository;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public final class ReserveFinder {
    private final ReserveRepository repository;

    public ReserveFinder(ReserveRepository repository) {
        this.repository = repository;
    }

    public Reserve execute(ReserveId id) {
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException("vehicle not found"));
    }
}
