package ar.edu.ungs.fleet_manager.enterprises.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.enterprises.application.add.EnterpriseModuleAdder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PutEnterpriseController {
    private final EnterpriseModuleAdder adder;

    public PutEnterpriseController(EnterpriseModuleAdder adder) {
        this.adder = adder;
    }

    @PostMapping("/api/enterprises/{id}/modules/{module}")
    public ResponseEntity<?> handle(@PathVariable String id, @PathVariable String module) {
        this.adder.execute(id, module);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
