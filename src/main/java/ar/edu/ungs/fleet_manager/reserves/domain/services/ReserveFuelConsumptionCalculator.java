package ar.edu.ungs.fleet_manager.reserves.domain.services;

import ar.edu.ungs.fleet_manager.reserves.domain.Reserve;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveId;
import ar.edu.ungs.fleet_manager.trips.domain.Trip;
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


    public double execute(Trip trip, Vehicle vehicle){
        Double distance = Double.parseDouble(trip.routes().getFirst().distance().replace(" km", ""));

        return (distance * vehicle.fuelConsumption().value()) / 100;
    }
}
