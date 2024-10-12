package ar.edu.ungs.fleet_manager.controls.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.controls.application.ControlResponse;
import ar.edu.ungs.fleet_manager.controls.application.find.ControlByIdFinder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class GetControlRestController {
    private final ControlByIdFinder finder;

    public GetControlRestController(ControlByIdFinder finder) {
        this.finder = finder;
    }

    @GetMapping ("/api/controls/{id}")
    public ResponseEntity<?> handle(@PathVariable String id) {
        ControlResponse control = this.finder.execute(id);

        return ResponseEntity.ok(control);

    }
}
