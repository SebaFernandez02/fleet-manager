package ar.edu.ungs.fleet_manager.reserves.domain;

import ar.edu.ungs.fleet_manager.users.domain.UserId;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;

import java.util.List;
import java.util.Optional;

public interface ReserveRepository {
    void save(Reserve reserve);

    Optional<Reserve> findById(ReserveId id);

    List<Reserve> findByUserId(UserId userId);

    List<Reserve> findByVehicleId(VehicleId vehicleId);

    List<Reserve> searchAll();
}
