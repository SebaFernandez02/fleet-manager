package ar.edu.ungs.fleet_manager.vehicles.domain;

import ar.edu.ungs.fleet_manager.vehicles.application.VehicleInfoRequest;

import java.time.LocalDateTime;
import java.util.Objects;

public final class Vehicle {
    private final VehicleId id;
    private  VehicleModel model;
    private  VehicleBrand brand;
    private  VehicleYear year;
    private VehicleStatus status;
    private Coordinates coordinates;
    private final LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    public Vehicle(VehicleId id,
                   VehicleModel model,
                   VehicleBrand brand,
                   VehicleYear year,
                   VehicleStatus status,
                   Coordinates coordinates,
                   LocalDateTime dateCreated,
                   LocalDateTime dateUpdated) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.year = year;
        this.status = status;
        this.coordinates = coordinates;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    public static Vehicle create(String id,
                                 String model,
                                 String brand,
                                 Integer year,
                                 Double latitude,
                                 Double longitude) {
        final String initialStatus = "AVAILABLE";

        return build(id,
                     model,
                     brand,
                     year,
                     initialStatus,
                     latitude,
                     longitude,
                     LocalDateTime.now(),
                     LocalDateTime.now());
    }

    public static Vehicle build(String id,
                                String model,
                                String brand,
                                Integer year,
                                String status,
                                Double latitude,
                                Double longitude,
                                LocalDateTime dateCreated,
                                LocalDateTime dateUpdated) {
        return new Vehicle(new VehicleId(id),
                new VehicleModel(model),
                new VehicleBrand(brand),
                new VehicleYear(year),
                VehicleStatus.parse(status),
                new Coordinates(latitude, longitude),
                dateCreated,
                dateUpdated);
    }

    public VehicleId id() {
        return id;
    }

    public VehicleModel model() {
        return model;
    }

    public VehicleYear year() {
        return year;
    }

    public VehicleBrand brand() {
        return brand;
    }

    public VehicleStatus status() {
        return status;
    }

    public void unavailabler(){
        this.status = VehicleStatus.UNAVAILABLE;
        this.dateUpdated = LocalDateTime.now();
    }

    public void release() {
        this.status = VehicleStatus.AVAILABLE;
        this.dateUpdated = LocalDateTime.now();
    }

    public void reserve() {
        this.status = VehicleStatus.RESERVED;
        this.dateUpdated = LocalDateTime.now();
    }

    public void maintenance() {
        this.status = VehicleStatus.MAINTENANCE;
        this.dateUpdated = LocalDateTime.now();
    }

    public Coordinates coordinates() {
        return coordinates;
    }

    public void updateCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        this.dateUpdated = LocalDateTime.now();
    }

    public boolean isNotAvailable() { return VehicleStatus.UNAVAILABLE.equals(this.status);}

    public boolean isReserved() {
        return VehicleStatus.RESERVED.equals(this.status);
    }

    public boolean isAvailable() {
        return VehicleStatus.AVAILABLE.equals(this.status);
    }

    public boolean isInMaintenance() {
        return VehicleStatus.MAINTENANCE.equals(this.status);
    }

    public LocalDateTime dateCreated() {
        return dateCreated;
    }

    public LocalDateTime dateUpdated() {
        return dateUpdated;
    }

    public void updateInfo(VehicleInfoRequest request){
        this.model = new VehicleModel(request.model());
        this.brand = new VehicleBrand(request.brand());
        this.year = new VehicleYear(request.year());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id) && Objects.equals(model, vehicle.model) && Objects.equals(brand, vehicle.brand) && status == vehicle.status && Objects.equals(coordinates, vehicle.coordinates) && Objects.equals(dateCreated, vehicle.dateCreated) && Objects.equals(dateUpdated, vehicle.dateUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, brand, status, coordinates, dateCreated, dateUpdated);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", model=" + model +
                ", brand=" + brand +
                ", status=" + status +
                ", coordinates=" + coordinates +
                ", dateCreated=" + dateCreated +
                ", dateUpdated=" + dateUpdated +
                '}';
    }
}
