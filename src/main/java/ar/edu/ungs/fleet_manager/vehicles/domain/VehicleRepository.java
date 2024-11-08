package ar.edu.ungs.fleet_manager.vehicles.domain;

import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository {

    void save(Vehicle vehicle);

    Optional<Vehicle> findById(VehicleId id);

    List<Vehicle> searchAll(EnterpriseId enterpriseId);

    List<Vehicle> searchAllByModel(EnterpriseId enterpriseId, VehicleBrand brand, VehicleModel model, VehicleYear year);
}
