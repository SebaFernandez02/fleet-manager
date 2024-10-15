package ar.edu.ungs.fleet_manager.orders.domain;

import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderId;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public final class Order {
    private final OrderId id;
    private OrderStatus status;
    private final ProviderId provider;
    private final List<OrderProduct> products;
    private final OrderAmount amount;
    private final LocalDateTime dateCreated;
    private final LocalDateTime dateUpdated;

    public Order(OrderId id, ProviderId provider, List<OrderProduct> products, OrderAmount amount, LocalDateTime dateCreated, LocalDateTime dateUpdated, OrderStatus status) {
        this.id = id;
        this.provider = provider;
        this.products = products;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.amount = amount;
        this.status = status;
    }

    public static Order create(String providerId){
        final String initialStatus = "CREATED";

        return build(UUID.randomUUID().toString(),
                    providerId,
                    new ArrayList<>(),
                    BigDecimal.ZERO,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    initialStatus);
    }

    public static Order build(String id,
                              String providerId,
                              List<OrderProduct> products,
                              BigDecimal amount,
                              LocalDateTime dateCreated,
                              LocalDateTime dateUpdated,
                              String status){
        return new Order(new OrderId(id),
                        new ProviderId(providerId),
                        products,
                        new OrderAmount(amount),
                        dateCreated,
                        dateUpdated,
                        OrderStatus.parse(status));
    }

    public OrderId id() {
        return id;
    }

    public ProviderId providerId() {
        return provider;
    }

    public List<OrderProduct> products() {return products;}

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

    public boolean isCompleted() {
        return this.status.equals(OrderStatus.COMPLETED);
    }

    public void setStatus(OrderStatus statusToUpdate){
        if(statusToUpdate.equals(this.status)){
            throw new InvalidParameterException("The Order status is already " + "'" +this.status.name() + "'");
        }

        this.status = statusToUpdate;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(provider, order.provider) &&
                Objects.equals(products, order.products) &&
                Objects.equals(amount, order.amount) &&
                Objects.equals(dateCreated, order.dateCreated) &&
                Objects.equals(dateUpdated, order.dateUpdated) &&
                status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, provider, products, amount, dateCreated, dateUpdated, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", provider=" + provider +
                ", products=" + products +
                ", amount=" + amount +
                ", dateCreated=" + dateCreated +
                ", dateUpdated=" + dateUpdated +
                ", status=" + status +
                '}';
    }

    public void add(ProductId productId, Quantity quantity, BigDecimal amount) {
        OrderProduct orderProduct = new OrderProduct(productId, quantity, amount);

        this.products.add(orderProduct);
    }
}
