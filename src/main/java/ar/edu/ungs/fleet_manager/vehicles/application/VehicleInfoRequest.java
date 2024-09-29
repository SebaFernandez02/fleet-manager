package ar.edu.ungs.fleet_manager.vehicles.application;

public record VehicleInfoRequest(String model, String brand, Integer year) {

    public static VehicleInfoRequest build(String model, String brand, Integer year){
        return new VehicleInfoRequest(model, brand, year);
    }
}
