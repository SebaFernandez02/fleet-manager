package ar.edu.ungs.fleet_manager.controls.application;

public record ControlPredictiveRequest(String subject,
                                       String description,
                                       String brand,
                                       String model,
                                       Integer year,
                                       String priority,
                                       String operatorId) {
}
