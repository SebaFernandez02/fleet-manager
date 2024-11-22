package ar.edu.ungs.fleet_manager.configs.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.configs.application.ConfigRequest;
import ar.edu.ungs.fleet_manager.configs.application.create.ConfigCreator;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class PutConfigRestController {
    private final ConfigCreator creator;

    public PutConfigRestController(ConfigCreator creator) {
        this.creator = creator;
    }

    @PutMapping ("/api/enterprises/{enterpriseId}/configs")
    public ResponseEntity<?> handle(@PathVariable String enterpriseId, @RequestBody ConfigRequest request){
        this.creator.execute(request, new EnterpriseId(enterpriseId));

        return ResponseEntity.ok().build();
    }
}
