package ar.edu.ungs.fleet_manager.orders.application.create;

import ar.edu.ungs.fleet_manager.orders.domain.OrderTemplate;
import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderId;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;



@Component
public class OrderTemplateCreator {
    public static OrderTemplate create(Product product){
        return OrderTemplate.build(product.id().value(),
                                                          product.prefProvider().map(ProviderId::value)
                                                          .orElse(""),
                                                          product.minStock().value()-product.quantity().value(),
                                                           calculateAmount(product.price().value(), product.quantity().value()));
    }
    public static BigDecimal calculateAmount(BigDecimal amount, Integer quantity){

        return amount.multiply(BigDecimal.valueOf(quantity));

    }
}

