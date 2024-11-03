package ar.edu.ungs.fleet_manager.controls.domain;

import ar.edu.ungs.fleet_manager.orders.domain.OrderProduct;
import ar.edu.ungs.fleet_manager.orders.domain.Quantity;
import ar.edu.ungs.fleet_manager.products.domain.ProductId;
import ar.edu.ungs.fleet_manager.users.domain.UserId;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class Control {
    private final ControlId id;
    private final ControlType type;
    private ControlStatus status;
    private UserId operatorId;
    private final ControlSubject subject;
    private final ControlDescription description;
    private final VehicleId vehicleId;
    private final ControlPriority priority;
    private final LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private final List<ControlProduct> productList;

    public Control(ControlId id,
                   ControlType type,
                   ControlStatus status,
                   UserId operatorId,
                   ControlSubject subject,
                   ControlDescription description,
                   VehicleId vehicleId,
                   ControlPriority priority,
                   LocalDateTime dateCreated,
                   LocalDateTime dateUpdated,
                   List<ControlProduct> productList) {
        this.id = id;
        this.type = type;
        this.subject = subject;
        this.description = description;
        this.vehicleId = vehicleId;
        this.priority = priority;
        this.status = status;
        this.operatorId = operatorId;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.productList = new ArrayList<>(productList);
    }

    public static Control create(String type,
                                 String subject,
                                 String description,
                                 String vehicleId,
                                 String priority,
                                 String operatorId){
        return build(UUID.randomUUID().toString(),
                     type,
                "TODO",
                     operatorId,
                     subject,
                     description,
                     vehicleId,
                     priority,
                     LocalDateTime.now(), LocalDateTime.now(), new ArrayList<>());
    }

    public static Control build(String id,
                                String type,
                                String status,
                                String operatorId,
                                String subject,
                                String description,
                                String vehicleId,
                                String priority,
                                LocalDateTime dateCreated,
                                LocalDateTime dateUpdated, List<ControlProduct> productList){
        return new Control(new ControlId(id),
                           ControlType.parse(type),
                           ControlStatus.parse(status),
                           operatorId != null ? new UserId(operatorId) : null,
                           new ControlSubject(subject),
                           new ControlDescription(description),
                           new VehicleId(vehicleId),
                           ControlPriority.parse(priority),
                           dateCreated, dateUpdated, productList);
    }

    public ControlId id() {
        return id;
    }

    public ControlType type() {
        return type;
    }

    public ControlSubject subject() {
        return subject;
    }

    public ControlDescription description() {
        return description;
    }

    public VehicleId vehicleId() {
        return vehicleId;
    }

    public ControlPriority priority() {
        return priority;
    }

    public LocalDateTime dateCreated() {
        return dateCreated;
    }

    public LocalDateTime dateUpdated() {
        return dateUpdated;
    }

    public ControlStatus status() {
        return status;
    }

    public UserId operatorId() {
        return operatorId;
    }

    public List<ControlProduct> products() {return productList;}

    public void setStatus(ControlStatus status) {
        this.status = status;
        this.dateUpdated = LocalDateTime.now();
    }

    public void setOperatorId(UserId operatorId) {
        this.operatorId = operatorId;
        this.dateUpdated = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Control control = (Control) o;
        return Objects.equals(id, control.id) && type == control.type && Objects.equals(subject, control.subject) && Objects.equals(description, control.description) && Objects.equals(vehicleId, control.vehicleId) && priority == control.priority && Objects.equals(dateCreated, control.dateCreated) && status == control.status && Objects.equals(operatorId, control.operatorId) && Objects.equals(productList, control.productList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, status, operatorId, subject, description, vehicleId, priority, dateCreated, dateUpdated, productList);
    }

    @Override
    public String toString() {
        return "Control{" +
                "id=" + id +
                ", type=" + type +
                ", status=" + status +
                ", operatorId=" + operatorId +
                ", subject=" + subject +
                ", description=" + description +
                ", vehicleId=" + vehicleId +
                ", priority=" + priority +
                ", dateCreated=" + dateCreated +
                ", dateUpdated=" + dateUpdated +
                ", productList=" + productList +
                '}';
    }

    public void addProducts(ProductId productId, Quantity quantity) {
        ControlProduct controlProduct = new ControlProduct(productId, quantity);

        this.productList.add(controlProduct);
    }

    public void deleteProducts(){

        this.productList.clear();
    }


}
