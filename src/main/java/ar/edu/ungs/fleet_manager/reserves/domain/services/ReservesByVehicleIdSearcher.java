package ar.edu.ungs.fleet_manager.reserves.domain.services;

import ar.edu.ungs.fleet_manager.reserves.domain.Reserve;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveRepository;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ReservesByVehicleIdSearcher {
    private final ReserveRepository repository;

    public ReservesByVehicleIdSearcher(ReserveRepository repository) {
        this.repository = repository;
    }

    public List<Reserve> execute(VehicleId id) {
        return this.repository.findByVehicleId(id);
    }
}
