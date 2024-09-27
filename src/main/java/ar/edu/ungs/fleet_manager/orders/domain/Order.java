package ar.edu.ungs.fleet_manager.orders.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public final class Order {
    private OrderId id;
    private OrderProvider provider;
    private OrderProduct product;
    private OrderQuantity quantity;
    private OrderAmount amount;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private OrderStatus status;


    public Order(OrderId id, OrderProvider provider, OrderProduct product, OrderQuantity quantity, OrderAmount amount,LocalDateTime dateCreated, LocalDateTime dateUpdated,  OrderStatus status) {
        this.id = id;
        this.provider = provider;
        this.product = product;
        this.quantity = quantity;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.amount = amount;
        this.status = status;
    }

    public static Order create(String provider,
                               String product,
                               Integer quantity,
                               Integer amount){

        final String initialStatus = "ACTIVE";

        return build(UUID.randomUUID().toString(),
                provider,
                product,
                quantity,
                amount,
                LocalDateTime.now(),
                LocalDateTime.now(),
                initialStatus);
    }

    public static Order build(String id,
                              String provider,
                              String product,
                              Integer quantity,
                              Integer amount,
                              LocalDateTime dateCreated,
                              LocalDateTime dateUpdated,
                              String status){

        return new Order(new OrderId(id),
                new OrderProvider(provider),
                new OrderProduct(product),
                new OrderQuantity(quantity),
                new OrderAmount(amount),
                dateCreated,
                dateUpdated,
                OrderStatus.parse(status));
    }

    public OrderId id() {
        return id;
    }

    public OrderProvider provider() {
        return provider;
    }

    public OrderProduct product() {
        return product;
    }

    public LocalDateTime dateCreated() {
        return dateCreated;
    }

    public LocalDateTime dateUpdated() {
        return dateUpdated;
    }

    public OrderAmount amount() {
        return amount;
    }

    public OrderStatus status() {
        return status;
    }

    public OrderQuantity quantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(provider, order.provider) && Objects.equals(product, order.product) && Objects.equals(amount, order.amount) && Objects.equals(dateCreated, order.dateCreated) && Objects.equals(dateUpdated, order.dateUpdated) && status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, provider, product, amount, dateCreated, dateUpdated, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", provider=" + provider +
                ", product=" + product +
                ", amount=" + amount +
                ", dateCreated=" + dateCreated +
                ", dateUpdated=" + dateUpdated +
                ", status=" + status +
                '}';
    }
}
