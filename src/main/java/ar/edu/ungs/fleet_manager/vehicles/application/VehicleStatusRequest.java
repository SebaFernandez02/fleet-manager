package ar.edu.ungs.fleet_manager.vehicles.application;

public record VehicleStatusRequest(String id, String status) {

    public static VehicleStatusRequest build(String id, VehicleStatusRequest request){
        return new VehicleStatusRequest(id, request.status);
    }
}
