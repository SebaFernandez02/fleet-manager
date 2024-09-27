package ar.edu.ungs.fleet_manager.reserves.application.find;

import ar.edu.ungs.fleet_manager.reserves.application.ReserveResponse;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveId;
import ar.edu.ungs.fleet_manager.reserves.domain.services.ReserveFinder;
import org.springframework.stereotype.Component;

@Component
public class ReserveByIdFinder {
    private final ReserveFinder finder;

    public ReserveByIdFinder(ReserveFinder finder) {
        this.finder = finder;
    }

    public ReserveResponse execute(String id) {
        var reserveId = new ReserveId(id);

        var reserve = this.finder.execute(reserveId);

        return ReserveResponse.map(reserve);
    }
}
