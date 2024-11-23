package ar.edu.ungs.fleet_manager.controls.domain.services;

import ar.edu.ungs.fleet_manager.controls.domain.*;
import ar.edu.ungs.fleet_manager.enterprises.domain.Enterprise;
import ar.edu.ungs.fleet_manager.reserves.domain.Reserve;
import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;

import java.util.List;

public interface ControlPredictor {
    ControlPrediction execute(Vehicle vehicle, List<Control> controls, List<Reserve> reserves, Enterprise enterprise);
}
