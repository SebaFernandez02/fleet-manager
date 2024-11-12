package ar.edu.ungs.fleet_manager.controls.application;

public record ControlRequest(String method,
                             String type,
                             String subject,
                             String description,
                             String vehicleId,
                             String priority,
                             String operatorId,
                             String enterpriseId,
                             String brand,
                             String model,
                             Integer year) {
    public static ControlRequest from(String type, String subject, String description, String vehicleId, String priority, String operatorId, String enterpriseId) {
        return new ControlRequest("default", type, subject, description, vehicleId, priority, operatorId, enterpriseId, null, null, null);
    }
}
