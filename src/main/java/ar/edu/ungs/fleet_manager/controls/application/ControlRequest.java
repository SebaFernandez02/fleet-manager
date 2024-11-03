package ar.edu.ungs.fleet_manager.controls.application;

public record ControlRequest(String method,
                             String type,
                             String subject,
                             String description,
                             String vehicleId,
                             String priority,
                             String operatorId,
                             String brand,
                             String model,
                             Integer year) {
    public static ControlRequest from(String type, String subject, String description, String vehicleId, String priority, String operatorId) {
        return new ControlRequest("default", type, subject, description, vehicleId, priority, operatorId, null, null, null);
    }

    public static ControlRequest from(String type, String subject, String description, String vehicleId, String priority) {
        return new ControlRequest("default", type, subject, description, vehicleId, priority, null, null, null, null);
    }
}
