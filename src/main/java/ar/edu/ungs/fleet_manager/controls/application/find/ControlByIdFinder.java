package ar.edu.ungs.fleet_manager.controls.application.find;

import ar.edu.ungs.fleet_manager.controls.application.ControlProductResponse;
import ar.edu.ungs.fleet_manager.controls.application.ControlResponse;
import ar.edu.ungs.fleet_manager.controls.domain.Control;
import ar.edu.ungs.fleet_manager.controls.domain.ControlId;
import ar.edu.ungs.fleet_manager.controls.domain.services.ControlFinder;
import ar.edu.ungs.fleet_manager.products.application.find.ProductByIdFinder;
import ar.edu.ungs.fleet_manager.users.domain.User;
import ar.edu.ungs.fleet_manager.users.domain.services.UserFinder;
import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.VehicleFinder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public final class ControlByIdFinder {
    private final ControlFinder controlFinder;
    private final VehicleFinder vehicleFinder;
    private final UserFinder userFinder;
    private final ProductByIdFinder productFinder;

    public ControlByIdFinder(ControlFinder controlFinder, VehicleFinder vehicleFinder, UserFinder userFinder, ProductByIdFinder productFinder) {
        this.controlFinder = controlFinder;
        this.vehicleFinder = vehicleFinder;
        this.userFinder = userFinder;
        this.productFinder = productFinder;
    }

    public ControlResponse execute(String id) {
        Control control = this.controlFinder.execute(new ControlId(id));
        Vehicle vehicle = this.vehicleFinder.execute(control.vehicleId());
        User user = Optional.ofNullable(control.operatorId()).isPresent() ? this.userFinder.execute(control.operatorId()) : null;
        List<ControlProductResponse> products = control.products().stream().map(x -> new ControlProductResponse(this.productFinder.execute(x.productId().value()), x.quantity().value())).toList();

        return ControlResponse.map(control, vehicle, user,products);
    }
}
