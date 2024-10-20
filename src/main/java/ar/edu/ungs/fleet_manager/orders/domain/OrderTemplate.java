package ar.edu.ungs.fleet_manager.orders.domain;


import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderId;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

public record OrderTemplate(ProductId productId,
                            ProviderId providerId,
                            Quantity quantity,
                            OrderAmount amount) {

    public static OrderTemplate build(String productId,
                                      String providerId,
                                      Integer quantity,
                                      BigDecimal amount) {



        return new OrderTemplate(new ProductId(productId),
                new ProviderId(providerId),
                new Quantity(quantity),
                new OrderAmount(amount)
        );
    }
}
