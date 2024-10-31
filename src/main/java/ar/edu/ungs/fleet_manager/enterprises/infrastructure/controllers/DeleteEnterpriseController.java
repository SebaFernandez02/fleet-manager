package ar.edu.ungs.fleet_manager.enterprises.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.enterprises.application.delete.EnterpriseDeleter;
import ar.edu.ungs.fleet_manager.enterprises.application.remove.EnterpriseModuleRemover;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteEnterpriseController {
    private final EnterpriseDeleter deleter;
    private final EnterpriseModuleRemover remover;

    public DeleteEnterpriseController(EnterpriseDeleter deleter, EnterpriseModuleRemover remover) {
        this.deleter = deleter;
        this.remover = remover;
    }

    @DeleteMapping("/api/enterprises/{id}/modules/{module}")
    public ResponseEntity<?> handle(@PathVariable String id, @PathVariable String module) {
        this.remover.execute(id, module);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/api/enterprises/{id}")
    public ResponseEntity<?> handle(@PathVariable String id) {
        this.deleter.execute(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
