package ar.edu.ungs.fleet_manager.controls.application.search;

import ar.edu.ungs.fleet_manager.controls.application.ControlProductResponse;
import ar.edu.ungs.fleet_manager.controls.application.ControlResponse;
import ar.edu.ungs.fleet_manager.controls.domain.Control;
import ar.edu.ungs.fleet_manager.controls.domain.ControlRepository;
import ar.edu.ungs.fleet_manager.orders.application.OrderProductResponse;
import ar.edu.ungs.fleet_manager.products.application.find.ProductByIdFinder;
import ar.edu.ungs.fleet_manager.products.domain.services.ProductFinder;
import ar.edu.ungs.fleet_manager.users.domain.Permissions;
import ar.edu.ungs.fleet_manager.users.domain.User;
import ar.edu.ungs.fleet_manager.users.domain.services.PermissionsFinder;
import ar.edu.ungs.fleet_manager.users.domain.services.UserFinder;
import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;
import ar.edu.ungs.fleet_manager.vehicles.domain.services.VehicleFinder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public final class ControlAllSearcher {
    private final ControlRepository repository;
    private final VehicleFinder vehicleFinder;
    private final UserFinder userFinder;
    private final ProductByIdFinder productFinder;

    public ControlAllSearcher(ControlRepository repository, VehicleFinder vehicleFinder, UserFinder userFinder, PermissionsFinder permissionsFinder, ProductByIdFinder productFinder) {
        this.repository = repository;
        this.vehicleFinder = vehicleFinder;
        this.userFinder = userFinder;
        this.productFinder = productFinder;
    }

    public List<ControlResponse> execute(){
        return this.repository.searchAll().stream().map(this::apply).toList();
    }

    private ControlResponse apply(Control control){
        Vehicle vehicle = this.vehicleFinder.execute(control.vehicleId());
        User user = Optional.ofNullable(control.operatorId()).isPresent() ? this.userFinder.execute(control.operatorId()) : null;
        List<ControlProductResponse> products = control.products().stream().map(x -> new ControlProductResponse(this.productFinder.execute(x.productId().value()), x.quantity().value())).toList();

        return ControlResponse.map(control, vehicle, user,products);
    }
}
