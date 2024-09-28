package ar.edu.ungs.fleet_manager.vehicles.application.update;

import ar.edu.ungs.fleet_manager.shared.aplication.CoordinatesRequest;
import ar.edu.ungs.fleet_manager.vehicles.domain.Coordinates;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleRepository;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.VehicleFinder;
import org.springframework.stereotype.Component;

@Component
public final class VehicleCoordinatesUpdater {
    private final VehicleFinder finder;
    private final VehicleRepository repository;

    public VehicleCoordinatesUpdater(VehicleFinder finder, VehicleRepository repository) {
        this.finder = finder;
        this.repository = repository;
    }

    public void execute(String id, CoordinatesRequest request) {
        var vehicle = this.finder.execute(new VehicleId(id));

        var newCoordinates = new Coordinates(request.latitude(), request.longitude());

        vehicle.updateCoordinates(newCoordinates);

        this.repository.updateCoordinates(vehicle);
    }
}
