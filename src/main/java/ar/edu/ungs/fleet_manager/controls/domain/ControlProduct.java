package ar.edu.ungs.fleet_manager.controls.domain;

import ar.edu.ungs.fleet_manager.orders.domain.Quantity;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;

public record ControlProduct(ProductId productId, Quantity quantity) {


    @Override
    public String toString(){
        return "OrderProduct {" +
                "productId= " + productId +
                ", quantity= " + quantity;

    }


}
