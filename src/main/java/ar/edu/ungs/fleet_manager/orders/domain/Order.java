package ar.edu.ungs.fleet_manager.orders.domain;

import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public final class Order {
    private final OrderId id;
    private OrderStatus status;
    private final ProviderId provider;
    private final ProductId product;
    private final OrderQuantity quantity;
    private final OrderAmount amount;
    private final LocalDateTime dateCreated;
    private final LocalDateTime dateUpdated;


    public Order(OrderId id, ProviderId provider, ProductId product, OrderQuantity quantity, OrderAmount amount,LocalDateTime dateCreated, LocalDateTime dateUpdated,  OrderStatus status) {
        this.id = id;
        this.provider = provider;
        this.product = product;
        this.quantity = quantity;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.amount = amount;
        this.status = status;
    }

    public static Order create(String providerId,
                               String productId,
                               Integer quantity,
                               BigDecimal amount){

        final String initialStatus = "CREATED";

        return build(UUID.randomUUID().toString(),
                    providerId,
                    productId,
                    quantity,
                    amount,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    initialStatus);
    }

    public static Order build(String id,
                              String providerId,
                              String productId,
                              Integer quantity,
                              BigDecimal amount,
                              LocalDateTime dateCreated,
                              LocalDateTime dateUpdated,
                              String status){

        return new Order(new OrderId(id),
                new ProviderId(providerId),
                new ProductId(productId),
                new OrderQuantity(quantity),
                new OrderAmount(amount),
                dateCreated,
                dateUpdated,
                OrderStatus.parse(status));
    }

    public static Order from(OrderTemplate template) {
        return create(template.providerId().value(),
                      template.productId().value(),
                      template.quantity().value(),
                      template.amount().value());
    }

    public OrderId id() {
        return id;
    }

    public ProviderId providerId() {
        return provider;
    }

    public ProductId productId() {
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

    public void setStatus(String status){
        this.status = OrderStatus.parse(status);
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
                ", providerId=" + provider +
                ", productId=" + product +
                ", amount=" + amount +
                ", dateCreated=" + dateCreated +
                ", dateUpdated=" + dateUpdated +
                ", status=" + status +
                '}';
    }
}
