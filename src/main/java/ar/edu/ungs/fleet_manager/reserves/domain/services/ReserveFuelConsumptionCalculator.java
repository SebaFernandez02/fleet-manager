package ar.edu.ungs.fleet_manager.reserves.domain.services;

import ar.edu.ungs.fleet_manager.reserves.domain.Reserve;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveId;
import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.VehicleFinder;
import org.springframework.stereotype.Component;

@Component
public final class ReserveFuelConsumptionCalculator {
    private final ReserveFinder reserveFinder;
    private final VehicleFinder vehicleFinder;

    public ReserveFuelConsumptionCalculator(ReserveFinder reserveFinder, VehicleFinder vehicleFinder) {
        this.reserveFinder = reserveFinder;
        this.vehicleFinder = vehicleFinder;
    }


    public double execute(ReserveId id){
        Reserve reserve = this.reserveFinder.execute(id);
        Vehicle vehicle = this.vehicleFinder.execute(reserve.vehicleId());
        Double distance = Double.parseDouble(reserve.trip().routes().getFirst().distance().replace(" km", ""));
        return (distance * vehicle.fuelConsumption().value()) / 100;
    }
}
