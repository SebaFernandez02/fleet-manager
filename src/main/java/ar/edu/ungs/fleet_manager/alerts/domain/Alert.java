package ar.edu.ungs.fleet_manager.alerts.domain;

import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public final class Alert {
    private final AlertId id;
    private final AlertPriority priority;
    private final AlertTitle title;
    private final AlertDescription description;
    private final EnterpriseId enterpriseId;
    private Boolean acknowledge;
    private final LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    public Alert(AlertId id,
                 AlertPriority priority,
                 AlertTitle title,
                 AlertDescription description,
                 EnterpriseId enterpriseId,
                 Boolean acknowledge,
                 LocalDateTime dateCreated,
                 LocalDateTime dateUpdated) {
        this.id = id;
        this.priority = priority;
        this.title = title;
        this.description = description;
        this.enterpriseId = enterpriseId;
        this.acknowledge = acknowledge;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    public static Alert factory(AlertStrategy strategy,
                                String vehicleId,
                                String enterpriseId) {
        return switch (strategy) {
            case CONTROL -> control(vehicleId, enterpriseId);
            case OUT_OF_RANGE -> outOfRange(vehicleId, enterpriseId);
        };
    }

    public static Alert control(String vehicleId, String enterpriseId) {
        return build(UUID.randomUUID().toString(),
                "HIGH",
                "Vehiculo averiado",
                String.format("El conductor del vehiculo %s ha solicitado asistencia. Por favor, revisar con urgencia.", vehicleId),
                Boolean.FALSE,
                enterpriseId,
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    public static Alert outOfRange(String vehicleId, String enterpriseId) {
        return build(UUID.randomUUID().toString(),
                "HIGH",
                "Vehiculo fuera de rango",
                String.format("El vehiculo %s está fuera de rango del viaje calculado. Por favor, revisar con urgencia.", vehicleId),
                Boolean.FALSE,
                enterpriseId,
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    public static Alert build(String id,
                              String priority,
                              String title,
                              String description,
                              Boolean acknowledge,
                              String enterpriseId,
                              LocalDateTime dateCreated,
                              LocalDateTime dateUpdated) {
        return new Alert(new AlertId(id),
                AlertPriority.valueOf(priority),
                new AlertTitle(title),
                new AlertDescription(description),
                new EnterpriseId(enterpriseId),
                acknowledge,
                dateCreated, dateUpdated);
    }

    public AlertId id() {
        return id;
    }

    public AlertPriority priority() {
        return priority;
    }

    public AlertTitle title() {
        return title;
    }

    public AlertDescription description() {
        return description;
    }

    public Boolean acknowledge() {
        return acknowledge;
    }

    public void ack() {
        this.acknowledge = Boolean.TRUE;
        this.dateUpdated = LocalDateTime.now();
    }

    public EnterpriseId enterpriseId() {
        return enterpriseId;
    }

    public LocalDateTime dateCreated() {
        return dateCreated;
    }

    public LocalDateTime dateUpdated() {
        return dateUpdated;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Alert) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.priority, that.priority) &&
                Objects.equals(this.title, that.title) &&
                Objects.equals(this.description, that.description) &&
                Objects.equals(this.acknowledge, that.acknowledge) &&
                Objects.equals(this.dateCreated, that.dateCreated) &&
                Objects.equals(this.dateUpdated, that.dateUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, priority, title, description, acknowledge, dateCreated, dateUpdated);
    }

    @Override
    public String toString() {
        return "Alert[" +
                "id=" + id + ", " +
                "priority=" + priority + ", " +
                "title=" + title + ", " +
                "description=" + description + ", " +
                "acknowledge=" + acknowledge + ", " +
                "dateCreated=" + dateCreated + ", " +
                "dateUpdated=" + dateUpdated + ']';
    }

}
