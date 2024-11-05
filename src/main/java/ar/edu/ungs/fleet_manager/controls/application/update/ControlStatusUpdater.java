package ar.edu.ungs.fleet_manager.controls.application.update;

import ar.edu.ungs.fleet_manager.controls.domain.*;
import ar.edu.ungs.fleet_manager.controls.domain.services.ControlFinder;
import ar.edu.ungs.fleet_manager.controls.domain.services.ControlProductQuantityHandler;
import ar.edu.ungs.fleet_manager.reserves.application.update.ReserveStatusUpdater;
import ar.edu.ungs.fleet_manager.reserves.domain.Reserve;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveStatus;
import ar.edu.ungs.fleet_manager.reserves.domain.services.ReserveFinder;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.VehicleMaintainer;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.VehicleReleaser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ControlStatusUpdater {
    private final ControlRepository repository;
    private final ControlFinder finder;
    private final VehicleMaintainer vehicleMaintainer;
    private final VehicleReleaser vehicleReleaser;
    private final ReserveFinder reserveFinder;
    private final ReserveStatusUpdater reserveStatusUpdater;
    private final ControlProductQuantityHandler controlProductQuantityHandler;

    public ControlStatusUpdater(ControlRepository repository,
                                ControlFinder finder,
                                VehicleMaintainer vehicleMaintainer,
                                VehicleReleaser vehicleReleaser,
                                ReserveFinder reserveFinder,
                                ReserveStatusUpdater reserveStatusUpdater,
                                ControlProductQuantityHandler controlProductQuantityHandler) {
        this.repository = repository;
        this.finder = finder;
        this.vehicleMaintainer = vehicleMaintainer;
        this.vehicleReleaser = vehicleReleaser;
        this.reserveFinder = reserveFinder;
        this.reserveStatusUpdater = reserveStatusUpdater;
        this.controlProductQuantityHandler = controlProductQuantityHandler;
    }

    public void execute(String id, String status){
        Control control = this.finder.execute(new ControlId(id));

        control.setStatus(ControlStatus.parse(status));

        if(ControlStatus.parse(status).equals(ControlStatus.DONE)){
            this.controlProductQuantityHandler.subtractProducts(control);
        }

        this.repository.save(control);

        this.solveVehicleStatus(control);
    }

    private void solveVehicleStatus(Control control) {
        if (List.of(ControlStatus.TODO, ControlStatus.DOING).contains(control.status())) {
            this.vehicleMaintainer.execute(control.vehicleId());
            return;
        }

        try {
            Reserve reserve = this.reserveFinder.execute(control.vehicleId(), ReserveStatus.CREATED);

            this.reserveStatusUpdater.execute(reserve.id().value(), "ACTIVATED");
        } catch (NotFoundException ignored) {
            this.vehicleReleaser.execute(control.vehicleId());
        }
    }




}
