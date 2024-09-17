package ar.edu.ungs.fleet_manager.shared.infrastructure.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetHealthCheckRestController {
    @GetMapping("/health-check")
    public ResponseEntity<?> handle() {
        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}
