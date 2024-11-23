package ar.edu.ungs.fleet_manager.reserves.application.update;

import ar.edu.ungs.fleet_manager.controls.domain.Control;
import ar.edu.ungs.fleet_manager.controls.domain.ControlId;
import ar.edu.ungs.fleet_manager.controls.domain.services.ControlFinder;
import ar.edu.ungs.fleet_manager.reserves.domain.Reserve;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveRepository;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveStatus;
import ar.edu.ungs.fleet_manager.reserves.domain.services.ReserveFinder;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.NotFoundException;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import org.springframework.stereotype.Component;

@Component
public final class ReserveControlUpdater {
    private final ReserveFinder reserveFinder;
    private final ReserveRepository repository;
    private final ControlFinder controlFinder;

    public ReserveControlUpdater(ReserveFinder reserveFinder, ReserveRepository repository, ControlFinder controlFinder) {
        this.reserveFinder = reserveFinder;
        this.controlFinder = controlFinder;
        this.repository = repository;
    }

    public void execute(String controlId){
        Control control = this.controlFinder.execute(new ControlId(controlId));

        try {
            Reserve reserve = this.reserveFinder.execute(control.vehicleId(), control.enterpriseId(), ReserveStatus.CREATED);

            reserve.setControl(controlId);

            this.repository.save(reserve);
        } catch (NotFoundException ignored) {

        }
    }
}
