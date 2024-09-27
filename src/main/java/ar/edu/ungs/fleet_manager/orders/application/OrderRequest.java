package ar.edu.ungs.fleet_manager.orders.application;

public record OrderRequest (String provider,
                            String product,
                            Integer quantity,
                            Integer amount){
}
