package ar.edu.ungs.fleet_manager.controls.application;

public record ControlTemplateRequest(String subject,
                                     String description,
                                     String priority,
                                     String assigned) {
}
