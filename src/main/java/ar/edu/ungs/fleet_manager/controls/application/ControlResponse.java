package ar.edu.ungs.fleet_manager.controls.application;

import ar.edu.ungs.fleet_manager.controls.domain.*;
import ar.edu.ungs.fleet_manager.users.application.UserResponse;
import ar.edu.ungs.fleet_manager.users.domain.Permissions;
import ar.edu.ungs.fleet_manager.users.domain.User;
import ar.edu.ungs.fleet_manager.vehicles.application.VehicleResponse;
import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;

import java.time.LocalDateTime;

public record ControlResponse (String id,
                               String type,
                               String subject,
                               String description,
                               VehicleResponse vehicle,
                               String priority,
                               LocalDateTime dateCreated,
                               LocalDateTime dateUpdated,
                               String status,
                               UserResponse operator) {
    public static ControlResponse map(Control control, Vehicle vehicle, User operator){
        return new ControlResponse(control.id().value(),
                                   control.type().toString(),
                                   control.subject().value(),
                                   control.description().value(),
                                   VehicleResponse.map(vehicle),
                                   control.priority().toString(),
                                   control.dateCreated(),
                                   control.dateUpdated(),
                                   control.status().toString(),
                                   UserResponse.map(operator));
    }
}
