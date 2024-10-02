package ar.edu.ungs.fleet_manager.orders.application;

import java.math.BigDecimal;

public record OrderRequest (String provider_id,
                            String product_id,
                            Integer quantity,
                            BigDecimal amount){
}
