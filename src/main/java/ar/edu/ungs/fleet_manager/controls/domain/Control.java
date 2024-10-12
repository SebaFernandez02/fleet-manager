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
    private VehicleId idVehicle;
    private ControlPriority priority;
    private final LocalDateTime date;
    private ControlStatus status;
    private UserId operatorId;

    public Control(ControlId id, ControlType type, ControlSubject subject, ControlDescription description, VehicleId idVehicle, ControlPriority priority, LocalDateTime date, ControlStatus status, ControlAssigned assigned) {
        this.id = id;
        this.type = type;
        this.subject = subject;
        this.description = description;
        this.idVehicle = idVehicle;
        this.priority = priority;
        this.date = date;
        this.status = status;
        this.assigned = assigned;
    }

 public static Control create(String type,
                                 String subject,
                                 String description,
                                 String idVehicle) {
        String assigned = "";
        String priority = type.equalsIgnoreCase("PREDICTIVE") ? "LOW" : "HIGH";

        return build(UUID.randomUUID().toString(),
                     type,
                     subject,
                     description,
                     idVehicle,
                     priority,
                     LocalDateTime.now(),
                     "TODO",
                     assigned);
    }

    public static Control build(String id,
                                String type,
                                String subject,
                                String description,
                                String idVehicle,
                                String priority,
                                LocalDateTime date,
                                String status,
                                String assigned){

        return new Control(new ControlId(id),
                           ControlType.parse(type),
                           new ControlSubject(subject),
                           new ControlDescription(description),
                           new VehicleId(idVehicle),
                           ControlPriority.parse(priority),
                           date,
                           ControlStatus.parse(status),
                           new ControlAssigned(assigned));
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

    public VehicleId idVehicle() {
        return idVehicle;
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

    public ControlAssigned assigned() {
        return assigned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Control control = (Control) o;
        return Objects.equals(id, control.id) && type == control.type && Objects.equals(subject, control.subject) && Objects.equals(description, control.description) && Objects.equals(idVehicle, control.idVehicle) && priority == control.priority && Objects.equals(date, control.date) && status == control.status && Objects.equals(assigned, control.assigned);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, subject, description, idVehicle, priority, date, status, assigned);
    }

    @Override
    public String toString() {
        return "Control{" +
                "id=" + id +
                ", type=" + type +
                ", subject=" + subject +
                ", description=" + description +
                ", idVehicle=" + idVehicle +
                ", priority=" + priority +
                ", date=" + date +
                ", status=" + status +
                ", assigned=" + assigned +
                '}';
    }

    public void setStatus(ControlStatus status) {
        this.status = status;
    }
}
