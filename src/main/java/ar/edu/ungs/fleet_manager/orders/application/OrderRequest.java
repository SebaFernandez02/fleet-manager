package ar.edu.ungs.fleet_manager.orders.application;

public record OrderRequest (String providerId,
                            String productId,
                            Integer quantity,
                            Integer amount){
}
