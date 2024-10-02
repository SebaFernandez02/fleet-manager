package ar.edu.ungs.fleet_manager.orders.application;

public record OrderStatusRequest(String status) {

    public static OrderStatusRequest build(String status){
        return new OrderStatusRequest(status);
    }
}
