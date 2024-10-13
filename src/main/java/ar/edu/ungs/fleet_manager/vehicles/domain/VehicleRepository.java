package ar.edu.ungs.fleet_manager.vehicles.domain;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository {

    void save(Vehicle vehicle);

    Optional<Vehicle> findById(VehicleId id);

    List<Vehicle> searchAll();

    List<Vehicle> searchAllByModel(VehicleBrand brand, VehicleModel model, VehicleYear year);
}
