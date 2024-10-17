package ar.edu.ungs.fleet_manager.vehicles.application.search;

import ar.edu.ungs.fleet_manager.vehicles.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class VehiclesAllSearcherByModel {
    private final VehicleRepository repository;

    public VehiclesAllSearcherByModel(VehicleRepository repository) {
        this.repository = repository;
    }

    public List<Vehicle> execute (VehicleBrand brand, VehicleModel model, VehicleYear year){
        return this.repository.searchAllByModel(brand, model, year);
    }
}
