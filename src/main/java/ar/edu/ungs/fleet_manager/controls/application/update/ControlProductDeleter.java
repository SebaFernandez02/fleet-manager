package ar.edu.ungs.fleet_manager.controls.application.update;

import ar.edu.ungs.fleet_manager.controls.application.ControlAddProductRequest;
import ar.edu.ungs.fleet_manager.controls.domain.Control;
import ar.edu.ungs.fleet_manager.controls.domain.ControlId;
import ar.edu.ungs.fleet_manager.controls.domain.ControlRepository;
import ar.edu.ungs.fleet_manager.controls.domain.ControlStatus;
import ar.edu.ungs.fleet_manager.controls.domain.services.ControlFinder;
import ar.edu.ungs.fleet_manager.orders.domain.Quantity;
import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.products.domain.services.ProductFinder;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;
import org.springframework.stereotype.Component;

@Component
public final class ControlProductDeleter {
    private final ControlRepository repository;
    private final ProductFinder productFinder;
    private final ControlFinder controlFinder;

    public ControlProductDeleter(ControlRepository repository, ProductFinder productFinder, ControlFinder orderFinder) {
        this.repository = repository;
        this.productFinder = productFinder;
        this.controlFinder = orderFinder;
    }

    public void execute(String id) {
        Control control = this.controlFinder.execute(new ControlId(id));

        if(control.status().equals(ControlStatus.DONE)){
            throw new InvalidParameterException("The control is already finished");
        }


        control.deleteProducts();

        this.repository.save(control);
    }
}
