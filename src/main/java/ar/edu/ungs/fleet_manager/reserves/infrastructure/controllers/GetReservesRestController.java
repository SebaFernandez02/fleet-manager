package ar.edu.ungs.fleet_manager.reserves.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.reserves.application.ReserveResponse;
import ar.edu.ungs.fleet_manager.reserves.application.find.ReserveByIdFinder;
import ar.edu.ungs.fleet_manager.reserves.application.search.ReservesAllSearcher;
import ar.edu.ungs.fleet_manager.reserves.application.search.ReservesByUserSearcher;
import ar.edu.ungs.fleet_manager.reserves.application.search.ReservesByVehicleSearcher;
import ar.edu.ungs.fleet_manager.reserves.application.search.ReservesSearcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetReservesRestController {
    private final ReservesSearcher searcher;

    public GetReservesRestController(ReservesSearcher searcher) {
        this.searcher = searcher;
    }

    @GetMapping("/api/reserves")
    public ResponseEntity<List<ReserveResponse>> handle(@RequestParam(defaultValue = "", required = false) String vehicleId,
                                                        @RequestParam(defaultValue = "", required = false) String userId,
                                                        @RequestParam(name = "enterprise_id", required = true) String enterpriseId) {
        var result = searcher.execute(vehicleId, userId, enterpriseId);

        return ResponseEntity.ok(result);
    }
}
