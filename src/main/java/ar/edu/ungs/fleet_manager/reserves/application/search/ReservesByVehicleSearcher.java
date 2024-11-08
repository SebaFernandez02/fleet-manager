package ar.edu.ungs.fleet_manager.reserves.application.search;

import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import ar.edu.ungs.fleet_manager.reserves.application.ReserveResponse;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveRepository;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ReservesByVehicleSearcher {
    private final ReserveRepository repository;

    public ReservesByVehicleSearcher(ReserveRepository repository) {
        this.repository = repository;
    }

    public List<ReserveResponse> execute(String id, EnterpriseId enterpriseId) {
        var vehicleId = new VehicleId(id);

        return this.repository.findByVehicleId(vehicleId, enterpriseId)
                .stream()
                .map(ReserveResponse::map)
                .toList();
    }
}
