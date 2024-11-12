package ar.edu.ungs.fleet_manager.controls.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.controls.application.ControlRequest;
import ar.edu.ungs.fleet_manager.controls.application.create.ControlCreator;
import ar.edu.ungs.fleet_manager.controls.application.create.DefaultControlCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@RestController
public final class PostControlRestController {
    private final Map<String, ControlCreator> creators;

    public PostControlRestController(@Qualifier("default") ControlCreator defaultCreator,
                                     @Qualifier("massive") ControlCreator massiveCreator,
                                     @Qualifier("predictive") ControlCreator predictiveCreator) {
        this.creators = Map.of("default", defaultCreator,
                            "massive", massiveCreator,
                            "predictive", predictiveCreator);
    }

    @PostMapping ("/api/controls")
    public ResponseEntity<?> handle(@RequestBody ControlRequest request){
        creators.get(Optional.ofNullable(request.method().toLowerCase(Locale.ROOT)).orElse("default")).execute(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
