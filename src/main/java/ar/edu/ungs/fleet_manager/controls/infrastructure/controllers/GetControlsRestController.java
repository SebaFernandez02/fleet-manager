package ar.edu.ungs.fleet_manager.controls.infrastructure.controllers;


import ar.edu.ungs.fleet_manager.controls.application.ControlResponse;
import ar.edu.ungs.fleet_manager.controls.application.search.ControlAllSearcher;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetControlsRestController {
    private final ControlAllSearcher searcher;

    public GetControlsRestController(ControlAllSearcher searcher) {
        this.searcher = searcher;
    }

    @GetMapping ("/api/controls")
    public ResponseEntity<List<ControlResponse>> handle(@RequestParam("enterprise_id") String enterpriseId){
        List<ControlResponse> result = this.searcher.execute(new EnterpriseId(enterpriseId));

        return ResponseEntity.ok(result);
    }
}
