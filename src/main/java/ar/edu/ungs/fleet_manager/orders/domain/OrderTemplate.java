package ar.edu.ungs.fleet_manager.orders.domain;


import java.math.BigDecimal;

public final class OrderTemplate {
    private String provider;
    private String product;
    private Integer quantity;
    private BigDecimal amount;

    public OrderTemplate(String provider, String product, Integer quantity, BigDecimal amount ){
        this.provider = provider;
        this.product = product;
        this.amount = amount;
        this.quantity = quantity;
    }

    public String provider() {
        return provider;
    }

    public String product() {
        return product;
    }

    public Integer quantity() {
        return quantity;
    }

    public BigDecimal amount() {
        return amount;
    }
}
