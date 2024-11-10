package ar.edu.ungs.fleet_manager.orders.domain;

import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
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
    private final List<OrderProduct> items;
    private  OrderAmount amount;
    private final EnterpriseId enterpriseId;
    private final LocalDateTime dateCreated;
    private final LocalDateTime dateUpdated;

    public Order(OrderId id, ProviderId provider, List<OrderProduct> items, OrderAmount amount, LocalDateTime dateCreated, LocalDateTime dateUpdated, OrderStatus status, EnterpriseId enterpriseId) {
        this.id = id;
        this.provider = provider;
        this.items = items;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.amount = amount;
        this.status = status;
        this.enterpriseId = enterpriseId;
    }

    public static Order create(String providerId, String enterpriseId){
        final String initialStatus = "CREATED";

        return build(UUID.randomUUID().toString(),
                    providerId,
                    new ArrayList<>(),
                    BigDecimal.ZERO,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    initialStatus,
                    enterpriseId);
    }

    public static Order build(String id,
                              String providerId,
                              List<OrderProduct> products,
                              BigDecimal amount,
                              LocalDateTime dateCreated,
                              LocalDateTime dateUpdated,
                              String status,
                              String enterpriseId){
        return new Order(new OrderId(id),
                        new ProviderId(providerId),
                        products,
                        new OrderAmount(amount),
                        dateCreated,
                        dateUpdated,
                        OrderStatus.parse(status),
                        new EnterpriseId(enterpriseId));
    }

    public OrderId id() {
        return id;
    }

    public ProviderId providerId() {
        return provider;
    }

    public List<OrderProduct> items() {return items;}

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

    public void updateAmount(BigDecimal value){
        this.amount = new OrderAmount(this.amount.value().add(value));
    }

    public EnterpriseId enterpriseId() {
        return enterpriseId;
    }

    public ProviderId provider() {
        return provider;
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
                Objects.equals(items, order.items) &&
                Objects.equals(amount, order.amount) &&
                Objects.equals(dateCreated, order.dateCreated) &&
                Objects.equals(dateUpdated, order.dateUpdated) &&
                status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, provider, items, amount, dateCreated, dateUpdated, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", provider=" + provider +
                ", products=" + items +
                ", amount=" + amount +
                ", dateCreated=" + dateCreated +
                ", dateUpdated=" + dateUpdated +
                ", status=" + status +
                '}';
    }

    public void add(ProductId productId, Quantity quantity, BigDecimal amount) {
        OrderProduct orderProduct = new OrderProduct(productId, quantity, amount);
        updateAmount(amount);
        this.items.add(orderProduct);
    }

    public void addExistingproduct(OrderProduct product, BigDecimal amount){
        for(OrderProduct p : items){
            if(p.productId().equals(product.productId())){
                this.items.remove(p);
                this.items.add(product);
                updateAmount(amount);

            }
        }
    }
}
