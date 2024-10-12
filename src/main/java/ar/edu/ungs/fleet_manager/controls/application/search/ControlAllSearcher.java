package ar.edu.ungs.fleet_manager.controls.application.search;

import ar.edu.ungs.fleet_manager.controls.application.ControlRequest;
import ar.edu.ungs.fleet_manager.controls.application.ControlResponse;
import ar.edu.ungs.fleet_manager.controls.domain.Control;
import ar.edu.ungs.fleet_manager.controls.domain.ControlRepository;
import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.VehicleFinder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ControlAllSearcher {
    private final ControlRepository repository;
    private final VehicleFinder vehicleFinder;

    public ControlAllSearcher(ControlRepository repository, VehicleFinder vehicleFinder) {
        this.repository = repository;
        this.vehicleFinder = vehicleFinder;
    }

    public List<ControlResponse> execute(){
        return this.repository.searchAll().stream().map(this::apply).toList();
    }

    private ControlResponse apply (Control control){
        Vehicle vehicle = this.vehicleFinder.execute(control.idVehicle());

        return ControlResponse.map(control, vehicle);
    }
}
