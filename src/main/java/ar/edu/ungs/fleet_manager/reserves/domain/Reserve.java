package ar.edu.ungs.fleet_manager.reserves.domain;

import ar.edu.ungs.fleet_manager.users.domain.User;
import ar.edu.ungs.fleet_manager.users.domain.UserId;
import ar.edu.ungs.fleet_manager.vehicles.domain.Coordinates;
import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;

import java.time.LocalDateTime;

public final class Reserve {
    private final ReserveId id;
    private ReserveStatus status;
    private final VehicleId vehicleId;
    private final UserId userId;
    private final Trip trip;
    private final LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    public Reserve(ReserveId id,
                   ReserveStatus status,
                   VehicleId vehicleId,
                   UserId userId,
                   Trip trip,
                   LocalDateTime dateCreated,
                   LocalDateTime dateUpdated) {
        this.id = id;
        this.status = status;
        this.vehicleId = vehicleId;
        this.userId = userId;
        this.trip = trip;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    public static Reserve create(Vehicle vehicle,
                                 User user,
                                 Trip trip) {
        ReserveStatus status = ReserveStatus.CREATED;
        VehicleId vehicleId = vehicle.id();
        LocalDateTime dateTime = LocalDateTime.now();

        return new Reserve(ReserveId.create(), status, vehicleId, user.id(), trip, dateTime, dateTime);
    }

    public static Reserve build(String id,
                                String status,
                                String vehicleId,
                                String userId,
                                Trip trip,
                                LocalDateTime dateCreated,
                                LocalDateTime dateUpdated) {
        ReserveId reserveId = new ReserveId(id);
        ReserveStatus reserveStatus = ReserveStatus.valueOf(status);

        return new Reserve(reserveId, reserveStatus, new VehicleId(vehicleId), new UserId(userId), trip, dateCreated, dateUpdated);
    }

    public ReserveId id() {
        return id;
    }

    public ReserveStatus status() {
        return status;
    }

    public VehicleId vehicleId() {
        return vehicleId;
    }

    public UserId userId() {
        return userId;
    }

    public Trip trip() {
        return trip;
    }

    public LocalDateTime dateCreated() {
        return dateCreated;
    }

    public LocalDateTime dateUpdated() {
        return dateUpdated;
    }

    public void update(ReserveStatus status) {
        this.status = status;
        this.dateUpdated = LocalDateTime.now();
    }
}
