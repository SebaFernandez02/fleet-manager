package ar.edu.ungs.fleet_manager.reserves.domain.services;

import ar.edu.ungs.fleet_manager.reserves.domain.Reserve;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveId;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveRepository;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveStatus;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import ar.edu.ungs.fleet_manager.users.domain.UserId;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ReserveFinder {
    private final ReserveRepository repository;

    public ReserveFinder(ReserveRepository repository) {
        this.repository = repository;
    }

    public Reserve execute(ReserveId id) {
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException("vehicle not found"));
    }

    public Reserve execute(VehicleId id) {
        return this.repository.findByVehicleId(id)
                              .stream()
                              .filter(x -> x.status().equals(ReserveStatus.ACTIVATED))
                              .findFirst()
                              .orElseThrow(() -> new NotFoundException("vehicle not found"));
    }
}
