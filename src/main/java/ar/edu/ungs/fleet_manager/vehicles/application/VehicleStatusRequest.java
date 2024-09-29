package ar.edu.ungs.fleet_manager.vehicles.application;

public record VehicleStatusRequest(String status) {

    public static VehicleStatusRequest build(VehicleStatusRequest request){
        return new VehicleStatusRequest(request.status);
    }
}
