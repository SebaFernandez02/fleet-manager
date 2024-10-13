package ar.edu.ungs.fleet_manager.controls.application;

public record ControlRequest(String subject,
                             String description,
                             String vehicleId) {

}
