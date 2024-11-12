package ar.edu.ungs.fleet_manager.reserves.application.search;

import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import ar.edu.ungs.fleet_manager.reserves.application.ReserveResponse;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveRepository;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ReservesSearcher {
    private final ReservesAllSearcher allSearcher;
    private final ReservesByVehicleSearcher byVehicleSearcher;
    private final ReservesByUserSearcher byUserSearcher;

    public ReservesSearcher(ReservesAllSearcher allSearcher,
                            ReservesByVehicleSearcher byVehicleSearcher,
                            ReservesByUserSearcher byUserSearcher) {
        this.allSearcher = allSearcher;
        this.byVehicleSearcher = byVehicleSearcher;
        this.byUserSearcher = byUserSearcher;
    }

    public List<ReserveResponse> execute(String vehicleId, String userId, String enterprise) {
        if (vehicleId.isBlank() && !userId.isBlank()) {
            return this.byUserSearcher.execute(userId, new EnterpriseId(enterprise));
        }

        if (!vehicleId.isBlank() && userId.isBlank()) {
            return this.byVehicleSearcher.execute(vehicleId, new EnterpriseId(enterprise));
        }

        if (vehicleId.isBlank()) {
            return this.allSearcher.execute(new EnterpriseId(enterprise));
        }

        throw new InvalidParameterException("vehicle or user must be empty");
    }
}
