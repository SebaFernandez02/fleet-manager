package ar.edu.ungs.fleet_manager.controls.application.create;

import ar.edu.ungs.fleet_manager.controls.application.ControlRequest;
import ar.edu.ungs.fleet_manager.vehicles.application.search.VehiclesAllSearcherByModel;
import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleBrand;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleModel;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleYear;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("massive")
public class MassiveControlCreator implements ControlCreator{
    private final VehiclesAllSearcherByModel searcher;
    private final ControlCreator creator;

    public MassiveControlCreator(VehiclesAllSearcherByModel searcher, ControlCreator creator) {
        this.searcher = searcher;
        this.creator = creator;
    }

    public void execute(ControlRequest request) {
        List<Vehicle> vehicles = this.searcher.execute(new VehicleBrand(request.brand()),
                                                        new VehicleModel(request.model()),
                                                        new VehicleYear(request.year()));

        vehicles.stream().map(vehicle -> apply(vehicle, request)).forEach(this.creator::execute);
    }

    private ControlRequest apply(Vehicle vehicle, ControlRequest request) {
        return ControlRequest.from("CORRECTIVE",
                                    request.subject(),
                                    request.description(),
                                    vehicle.id().value(),
                                    request.priority(),
                                    request.operatorId());
    }
}
