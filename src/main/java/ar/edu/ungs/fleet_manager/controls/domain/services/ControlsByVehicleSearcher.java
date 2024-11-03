package ar.edu.ungs.fleet_manager.controls.domain.services;

import ar.edu.ungs.fleet_manager.controls.domain.Control;
import ar.edu.ungs.fleet_manager.controls.domain.ControlRepository;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ControlsByVehicleSearcher {
    private final ControlRepository repository;

    public ControlsByVehicleSearcher(ControlRepository repository) {
        this.repository = repository;
    }
    public List<Control> execute(VehicleId vehicleId){
        return this.repository.searchAll().stream().filter(x -> x.vehicleId().value().equals(vehicleId.value())).toList();
    }
}
