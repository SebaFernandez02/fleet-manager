package ar.edu.ungs.fleet_manager.controls.domain;

import ar.edu.ungs.fleet_manager.users.domain.UserId;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public final class Control {
    private final ControlId id;
    private ControlType type;
    private ControlSubject subject;
    private ControlDescription description;
    private VehicleId vehicleId;
    private ControlPriority priority;
    private final LocalDateTime date;
    private ControlStatus status;
    private UserId operatorId;

    public Control(ControlId id, ControlType type, ControlSubject subject, ControlDescription description, VehicleId vehicleId, ControlPriority priority, LocalDateTime date, ControlStatus status, UserId operatorId) {
        this.id = id;
        this.type = type;
        this.subject = subject;
        this.description = description;
        this.vehicleId = vehicleId;
        this.priority = priority;
        this.date = date;
        this.status = status;
        this.operatorId = operatorId;
    }

    public static Control create(String type,
                                 String subject,
                                 String description,
                                 String vehicleId,
                                 String operatorId){

        String priority = type.equalsIgnoreCase("PREDICTIVE") ? "LOW" : "HIGH";
        return build(UUID.randomUUID().toString(),
                     type,
                     subject,
                     description,
                     vehicleId,
                     priority,
                     LocalDateTime.now(),
                     "TODO",
                     operatorId);
    }

    public static Control build(String id,
                                String type,
                                String subject,
                                String description,
                                String vehicleId,
                                String priority,
                                LocalDateTime date,
                                String status,
                                String operatorId){

        return new Control(new ControlId(id),
                           ControlType.parse(type),
                           new ControlSubject(subject),
                           new ControlDescription(description),
                           new VehicleId(vehicleId),
                           ControlPriority.parse(priority),
                           date,
                           ControlStatus.parse(status),
                           new UserId(operatorId));
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

    public LocalDateTime date() {
        return date;
    }

    public ControlStatus status() {
        return status;
    }

    public UserId operatorId() {
        return operatorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Control control = (Control) o;
        return Objects.equals(id, control.id) && type == control.type && Objects.equals(subject, control.subject) && Objects.equals(description, control.description) && Objects.equals(vehicleId, control.vehicleId) && priority == control.priority && Objects.equals(date, control.date) && status == control.status && Objects.equals(operatorId, control.operatorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, subject, description, vehicleId, priority, date, status, operatorId);
    }

    @Override
    public String toString() {
        return "Control{" +
                "id=" + id +
                ", type=" + type +
                ", subject=" + subject +
                ", description=" + description +
                ", vehicleId=" + vehicleId +
                ", priority=" + priority +
                ", date=" + date +
                ", status=" + status +
                ", operatorId=" + operatorId +
                '}';
    }

    public void setStatus(ControlStatus status) {
        this.status = status;
    }

    public void setOperatorId(UserId operatorId) {
        this.operatorId = operatorId;
    }
}
