package ar.edu.ungs.fleet_manager.controls.application;

import ar.edu.ungs.fleet_manager.controls.domain.Control;
import ar.edu.ungs.fleet_manager.controls.domain.ControlTemplate;
import ar.edu.ungs.fleet_manager.vehicles.application.VehicleResponse;
import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;

public record ControlTemplateResponse(String id,
                                      String subject,
                                      String description,
                                      String priority,
                                      String assigned) {

    public static ControlTemplateResponse map(ControlTemplate control){
        return new ControlTemplateResponse( control.id().value(),
                                    control.subject().value(),
                                    control.description().value(),
                                    control.priority().toString(),
                                    control.assigned().value());
    }
}
