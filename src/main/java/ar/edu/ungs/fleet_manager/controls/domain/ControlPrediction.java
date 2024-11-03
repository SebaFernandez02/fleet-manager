package ar.edu.ungs.fleet_manager.controls.domain;

public record ControlPrediction(ControlType type, ControlSubject subject, ControlDescription description, ControlPriority priority) {
}
