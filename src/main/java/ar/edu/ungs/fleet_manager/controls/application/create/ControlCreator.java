package ar.edu.ungs.fleet_manager.controls.application.create;

import ar.edu.ungs.fleet_manager.controls.application.ControlRequest;
import ar.edu.ungs.fleet_manager.controls.domain.Control;
import ar.edu.ungs.fleet_manager.controls.domain.ControlRepository;
import ar.edu.ungs.fleet_manager.reserves.application.update.ReserveControlUpdater;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;
import ar.edu.ungs.fleet_manager.users.domain.User;
import ar.edu.ungs.fleet_manager.users.domain.UserId;
import ar.edu.ungs.fleet_manager.users.domain.services.UserFinder;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.VehicleFinder;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.VehicleMaintainer;
import org.springframework.stereotype.Component;

@Component
public final class ControlCreator {
    private final ControlRepository repository;
    private final UserFinder userFinder;
    private final VehicleFinder vehicleFinder;
    private final VehicleMaintainer vehicleMaintainer;
    private final ReserveControlUpdater reserveControlUpdater;

    public ControlCreator(ControlRepository repository, UserFinder userFinder, VehicleFinder vehicleFinder, VehicleMaintainer vehicleMaintainer, ReserveControlUpdater reserveControlUpdater) {
        this.repository = repository;
        this.userFinder = userFinder;
        this.vehicleFinder = vehicleFinder;
        this.vehicleMaintainer = vehicleMaintainer;
        this.reserveControlUpdater = reserveControlUpdater;
    }

    public void execute(ControlRequest request){
        this.ensureUserOperatorValid(request);
        this.ensureVehicleValid(request);

        Control control = Control.create(request.type(),
                                         request.subject(),
                                         request.description(),
                                         request.vehicleId(),
                                         request.priority(),
                                         request.operatorId(),
                                         request.enterpriseId());

        this.repository.save(control);
        this.vehicleMaintainer.execute(control.vehicleId());

        this.reserveControlUpdater.execute(control.id().value());
    }

    private void ensureVehicleValid(ControlRequest request) {
        vehicleFinder.execute(new VehicleId(request.vehicleId()));
    }

    private void ensureUserOperatorValid(ControlRequest request) {
        if (request.operatorId() == null) {
            return;
        }

        User user = this.userFinder.execute(new UserId(request.operatorId()));

        if (!user.isOperator()) {
            throw new InvalidParameterException("the user id is not a operator");
        }
    }
}
