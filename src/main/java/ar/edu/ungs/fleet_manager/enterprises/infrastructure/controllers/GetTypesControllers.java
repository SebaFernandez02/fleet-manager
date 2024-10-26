package ar.edu.ungs.fleet_manager.enterprises.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

@RestController
public class GetTypesControllers {
    @GetMapping("/api/subscription-methods")
    public ResponseEntity<?> handle() {
        var result = Arrays.stream(EnterpriseType.values())
                           .map(x -> Map.of("name", x.name(), "modules", Arrays.stream(x.modules()).map(Enum::name).toList()));

        return ResponseEntity.ok(result);
    }
}
