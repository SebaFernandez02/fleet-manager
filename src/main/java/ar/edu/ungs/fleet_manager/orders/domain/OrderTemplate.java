package ar.edu.ungs.fleet_manager.orders.domain;


import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderId;

import java.math.BigDecimal;

public record OrderTemplate(ProviderId providerId,
                            ProductId productId,
                            Quantity quantity,
                            OrderAmount amount) {

    public static OrderTemplate build(String providerId,
                                      String productId,
                                      Integer quantity,
                                      BigDecimal amount) {
        return new OrderTemplate(new ProviderId(providerId),
                                 new ProductId(productId),
                                 new Quantity(quantity),
                                 new OrderAmount(amount));
    }
}
