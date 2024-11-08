package ar.edu.ungs.fleet_manager.vehicles.application.search;

import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import ar.edu.ungs.fleet_manager.vehicles.application.VehicleResponse;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class VehiclesAllSearcher {
    private final VehicleRepository repository;

    public VehiclesAllSearcher(VehicleRepository repository) {
        this.repository = repository;
    }

    public List<VehicleResponse> execute(EnterpriseId enterpriseId) {
        return this.repository.searchAll(enterpriseId)
                              .stream()
                              .map(VehicleResponse::map)
                              .toList();
    }
}
