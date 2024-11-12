package ar.edu.ungs.fleet_manager.reserves.domain;

import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import ar.edu.ungs.fleet_manager.trips.domain.Trip;
import ar.edu.ungs.fleet_manager.users.domain.User;
import ar.edu.ungs.fleet_manager.users.domain.UserId;
import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;

import java.time.LocalDateTime;

public final class Reserve {
    private final ReserveId id;
    private ReserveStatus status;
    private final VehicleId vehicleId;
    private final UserId userId;
    private final Trip trip;
    private final EnterpriseId enterpriseId;
    private final LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private final LocalDateTime dateReserve;
    private final LocalDateTime dateFinishReserve;
    private final Double fuelConsumption;

    public Reserve(ReserveId id,
                   ReserveStatus status,
                   VehicleId vehicleId,
                   UserId userId,
                   Trip trip, EnterpriseId enterpriseId,
                   LocalDateTime dateCreated,
                   LocalDateTime dateUpdated,
                   LocalDateTime dateReserve,
                   LocalDateTime dateFinishReserve, Double fuelConsumption) {
        this.id = id;
        this.status = status;
        this.vehicleId = vehicleId;
        this.userId = userId;
        this.trip = trip;
        this.enterpriseId = enterpriseId;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.dateReserve = dateReserve;
        this.dateFinishReserve = dateFinishReserve;
        this.fuelConsumption = fuelConsumption;
    }

    public static Reserve create(Vehicle vehicle,
                                 User user,
                                 Trip trip,
                                 LocalDateTime dateReserve,
                                 LocalDateTime dateFinishReserve,
                                 Double fuelConsumption,
                                 String enterpriseId) {
        ReserveStatus status = ReserveStatus.CREATED;
        VehicleId vehicleId = vehicle.id();
        LocalDateTime dateTime = LocalDateTime.now();

        return new Reserve(ReserveId.create(), status, vehicleId, user.id(), trip, new EnterpriseId(enterpriseId), dateTime, dateTime, dateReserve, dateFinishReserve, fuelConsumption);
    }

    public static Reserve build(String id,
                                String status,
                                String vehicleId,
                                String userId,
                                Trip trip,
                                String enterpriseId,
                                LocalDateTime dateCreated,
                                LocalDateTime dateUpdated,
                                LocalDateTime dateReserve,
                                LocalDateTime dateFinishReserve,
                                Double fuelConsumption) {
        ReserveId reserveId = new ReserveId(id);
        ReserveStatus reserveStatus = ReserveStatus.valueOf(status);

        return new Reserve(reserveId, reserveStatus, new VehicleId(vehicleId), new UserId(userId), trip, new EnterpriseId(enterpriseId), dateCreated, dateUpdated, dateReserve, dateFinishReserve, fuelConsumption);
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

    public EnterpriseId enterpriseId() {
        return enterpriseId;
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

    public LocalDateTime dateReserve() {
        return dateReserve;
    }

    public LocalDateTime dateFinishReserve() {
        return dateFinishReserve;
    }

    public Double fuelConsumption() {
        return fuelConsumption;
    }

    public void update(ReserveStatus status) {
        this.status = status;
        this.dateUpdated = LocalDateTime.now();
    }

}
