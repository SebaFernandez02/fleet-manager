package ar.edu.ungs.fleet_manager.controls.domain.services;

import ar.edu.ungs.fleet_manager.controls.domain.Control;
import ar.edu.ungs.fleet_manager.products.domain.services.ProductQuantityRemover;
import org.springframework.stereotype.Component;

@Component
public class ControlProductQuantityHandler {

    private final ProductQuantityRemover quantityRemover;

    public ControlProductQuantityHandler( ProductQuantityRemover quantityRemover) {

        this.quantityRemover = quantityRemover;
    }

    public void subtractProducts(Control control){

        control.products().forEach(ControlProduct -> quantityRemover.execute(ControlProduct.productId(),ControlProduct.quantity()));

    }
}
