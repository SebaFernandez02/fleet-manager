package ar.edu.ungs.fleet_manager.vehicles.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.vehicles.application.VehicleResponse;
import ar.edu.ungs.fleet_manager.vehicles.application.find.VehicleByIdFinder;
import ar.edu.ungs.fleet_manager.vehicles.application.search.VehiclesAllSearcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetVehiclesRestController {
    private final VehiclesAllSearcher searcher;

    public GetVehiclesRestController(VehiclesAllSearcher searcher) {
        this.searcher = searcher;
    }

    @GetMapping("/api/vehicles")
    public ResponseEntity<List<VehicleResponse>> handle() {
        List<VehicleResponse> result = this.searcher.execute();

        return ResponseEntity.ok(result);
    }
}
