package ar.edu.ungs.fleet_manager.controls.infrastructure.controllers;


import ar.edu.ungs.fleet_manager.controls.application.ControlResponse;
import ar.edu.ungs.fleet_manager.controls.application.search.ControlAllSearcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetControlsRestController {
    private final ControlAllSearcher searcher;

    public GetControlsRestController(ControlAllSearcher searcher) {
        this.searcher = searcher;
    }

    @GetMapping ("/api/controls")
    public ResponseEntity<List<ControlResponse>> handle(){
        List<ControlResponse> result = this.searcher.execute();

        return ResponseEntity.ok(result);
    }
}
