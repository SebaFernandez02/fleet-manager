package ar.edu.ungs.fleet_manager.controls.application;

public record ControlRequest(String type,
                             String subject,
                             String description,
                             String vehicleId,
                             String operatorId) {

}
