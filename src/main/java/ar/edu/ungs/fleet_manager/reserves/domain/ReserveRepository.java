package ar.edu.ungs.fleet_manager.reserves.domain;

import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import ar.edu.ungs.fleet_manager.users.domain.UserId;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;

import java.util.List;
import java.util.Optional;

public interface ReserveRepository {
    void save(Reserve reserve);

    Optional<Reserve> findById(ReserveId id);

    List<Reserve> findByUserId(UserId userId, EnterpriseId enterpriseId);

    List<Reserve> findByVehicleId(VehicleId vehicleId, EnterpriseId enterpriseId);

    List<Reserve> searchAll(EnterpriseId enterpriseId);
}
