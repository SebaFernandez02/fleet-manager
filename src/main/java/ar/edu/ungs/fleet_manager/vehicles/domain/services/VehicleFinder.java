package ar.edu.ungs.fleet_manager.vehicles.domain.services;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleRepository;
import org.springframework.stereotype.Component;

@Component
public final class VehicleFinder {
    private final VehicleRepository repository;

    public VehicleFinder(VehicleRepository repository) {
        this.repository = repository;
    }

    public Vehicle execute(VehicleId id) {
        return this.repository.findById(id)
                              .orElseThrow(() -> new NotFoundException(String.format("vehicle %s not found", id.value())));
    }
}
