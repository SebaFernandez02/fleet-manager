package ar.edu.ungs.fleet_manager.controls.application;

import ar.edu.ungs.fleet_manager.controls.domain.*;
import ar.edu.ungs.fleet_manager.vehicles.application.VehicleResponse;
import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;

import java.time.LocalDateTime;

public record ControlResponse (String id,
                               String type,
                               String subject,
                               String description,
                               VehicleResponse vehicle,
                               String priority,
                               LocalDateTime date,
                               String status,
                               String assigned) {


    public static ControlResponse map(Control control, Vehicle vehicle){
        return new ControlResponse(control.id().value(),
                                   control.type().toString(),
                                   control.subject().value(),
                                   control.description().value(),
                                   VehicleResponse.map(vehicle),
                                   control.priority().toString(),
                                   control.date(),
                                   control.status().toString(),
                                   control.assigned().value());
    }
}