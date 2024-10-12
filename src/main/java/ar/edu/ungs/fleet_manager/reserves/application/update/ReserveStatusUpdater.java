package ar.edu.ungs.fleet_manager.reserves.application.update;

import ar.edu.ungs.fleet_manager.reserves.domain.Reserve;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveId;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveRepository;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveStatus;
import ar.edu.ungs.fleet_manager.reserves.domain.services.ReserveFinder;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.VehicleReleaser;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.VehicleReserver;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public final class ReserveStatusUpdater {
    private final ReserveFinder finder;
    private final ReserveRepository repository;
    private final VehicleReleaser vehicleReleaser;
    private final VehicleReserver vehicleReserver;

    public ReserveStatusUpdater(ReserveFinder finder, ReserveRepository repository, VehicleReleaser vehicleReleaser, VehicleReserver vehicleReserver) {
        this.finder = finder;
        this.repository = repository;
        this.vehicleReleaser = vehicleReleaser;
        this.vehicleReserver = vehicleReserver;
    }

    public void execute(String id, String status) {
        var reserveId = new ReserveId(id);
        var reserveStatus = ReserveStatus.valueOf(status.toUpperCase(Locale.ROOT));

        Reserve reserve = this.finder.execute(reserveId);

        reserve.update(reserveStatus);

        this.repository.save(reserve);

        this.solveVehicleState(reserve, reserveStatus);
    }

    private void solveVehicleState(Reserve reserve, ReserveStatus reserveStatus) {
        var vehicleId = reserve.vehicleId();

        switch (reserveStatus) {
            // case CREATED -> this.controlCreator.execute(vehicleId);
            case ACTIVATED -> this.vehicleReserver.execute(vehicleId);
            case REJECTED, COMPLETED, CANCELLED -> this.vehicleReleaser.execute(vehicleId);
        }
    }
}
