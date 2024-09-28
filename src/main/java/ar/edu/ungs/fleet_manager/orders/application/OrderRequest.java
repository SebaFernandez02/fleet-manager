package ar.edu.ungs.fleet_manager.orders.application;

import java.math.BigDecimal;

public record OrderRequest (String providerId,
                            String productId,
                            Integer quantity,
                            BigDecimal amount){
}
