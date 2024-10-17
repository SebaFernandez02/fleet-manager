package ar.edu.ungs.fleet_manager.orders.domain;

import ar.edu.ungs.fleet_manager.products.domain.ProductId;

import java.math.BigDecimal;

public record OrderProduct(ProductId productId, Quantity quantity, BigDecimal amount) {

    @Override
   public String toString(){
        return "OrderProduct {" +
                "productId= " + productId +
                ", quantity= " + quantity +
                ", amount=" + amount;

    }


}
