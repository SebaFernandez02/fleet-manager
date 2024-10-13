package ar.edu.ungs.fleet_manager.controls.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.controls.application.ControlPredictiveRequest;
import ar.edu.ungs.fleet_manager.controls.application.ControlRequest;
import ar.edu.ungs.fleet_manager.controls.application.create.ControlCreator;
import ar.edu.ungs.fleet_manager.vehicles.application.search.VehiclesAllSearcherByModel;
import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleBrand;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleModel;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleYear;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public final class PostPredictiveRestController {
    private final ControlCreator creator;
    private final VehiclesAllSearcherByModel searcher;

    public PostPredictiveRestController(ControlCreator creator, VehiclesAllSearcherByModel searcher) {
        this.creator = creator;
        this.searcher = searcher;
    }

    @PostMapping ("/api/controls/predictive")
    public ResponseEntity<?> handle(@RequestBody ControlPredictiveRequest request){

        List<Vehicle> vehiculos = this.searcher.execute(new VehicleBrand(request.brand()), new VehicleModel(request.model()), new VehicleYear(request.year()));

        for(Vehicle vehiculo : vehiculos){
            ControlRequest control = new ControlRequest("PREDICTIVE",
                                                        request.subject(),
                                                        request.description(),
                                                        vehiculo.id().value(),
                                                        request.priority(),
                                                        request.operatorId());
            this.creator.execute(control);
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
