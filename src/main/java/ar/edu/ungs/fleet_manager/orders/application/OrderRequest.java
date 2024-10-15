package ar.edu.ungs.fleet_manager.orders.application;

import java.math.BigDecimal;
import java.util.Map;

public record OrderRequest (String providerId,
                            Map<String,Integer> products,
                            BigDecimal amount){
}
