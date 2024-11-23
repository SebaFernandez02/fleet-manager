package ar.edu.ungs.fleet_manager.controls.application.create;

import ar.edu.ungs.fleet_manager.controls.application.ControlRequest;
import ar.edu.ungs.fleet_manager.controls.domain.services.ControlPredictor;
import ar.edu.ungs.fleet_manager.controls.domain.services.ControlsByVehicleSearcher;
import ar.edu.ungs.fleet_manager.enterprises.domain.services.EnterpriseFinder;
import ar.edu.ungs.fleet_manager.reserves.domain.services.ReservesByVehicleIdSearcher;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.VehicleFinder;
import org.springframework.stereotype.Component;

@Component("predictive")
public class PredictiveControlCreator implements ControlCreator {
    private final VehicleFinder vehicleFinder;
    private final ControlsByVehicleSearcher controlsSearcher;
    private final ReservesByVehicleIdSearcher reservesSearcher;
    private final ControlPredictor predictor;
    private final ControlCreator creator;
    private final EnterpriseFinder enterpriseFinder;

    public PredictiveControlCreator(VehicleFinder vehicleFinder,
                                    ControlsByVehicleSearcher controlsSearcher,
                                    ReservesByVehicleIdSearcher reservesSearcher,
                                    ControlPredictor predictor,
                                    ControlCreator creator, EnterpriseFinder enterpriseFinder) {
        this.vehicleFinder = vehicleFinder;
        this.controlsSearcher = controlsSearcher;
        this.reservesSearcher = reservesSearcher;
        this.predictor = predictor;
        this.creator = creator;
        this.enterpriseFinder = enterpriseFinder;
    }

    @Override
    public void execute(ControlRequest request) {
        var vehicleId = new VehicleId(request.vehicleId());

        var vehicle = this.vehicleFinder.execute(vehicleId);
        var controls = this.controlsSearcher.execute(vehicle.enterpriseId(), vehicleId);
        var reserves = this.reservesSearcher.execute(vehicleId, vehicle.enterpriseId());
        var enterprise = this.enterpriseFinder.execute(request.enterpriseId());



        var prediction = this.predictor.execute(vehicle, controls, reserves, enterprise);

        var newRequest = ControlRequest.from(prediction.type().name(),
                                             prediction.subject().value(),
                                             prediction.description().value(),
                                             vehicleId.value(),
                                             prediction.priority().name(),
                                             request.operatorId(),
                                            vehicle.enterpriseId().value());

        this.creator.execute(newRequest);
    }

}
