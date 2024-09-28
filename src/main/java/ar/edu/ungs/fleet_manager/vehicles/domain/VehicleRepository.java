package ar.edu.ungs.fleet_manager.vehicles.domain;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository {
    void save(Vehicle vehicle);

    void updateCoordinates(Vehicle vehicle);

    void saveStatus(Vehicle vehicle);

    Optional<Vehicle> findById(VehicleId id);

    List<Vehicle> searchAll();
}
