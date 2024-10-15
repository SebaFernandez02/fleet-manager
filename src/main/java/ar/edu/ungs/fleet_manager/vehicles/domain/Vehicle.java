package ar.edu.ungs.fleet_manager.vehicles.domain;


import java.time.LocalDateTime;
import java.util.Objects;

public final class Vehicle {
    private final VehicleId id;
    private VehicleModel model;
    private VehicleBrand brand;
    private VehicleYear year;

    private VehicleType type;
    private VehicleColor color;
    private VehicleFuel fuelType;
    private VehicleFuelMeasurement fuelMeasurement;
    private VehicleFuelConsumption fuelConsumption;
    private VehicleAxles cantAxles;
    private VehicleSeats cantSeats;
    private VehicleLoad load;
    private boolean hasTrailer;

    private VehicleStatus status;
    private Coordinates coordinates;
    private final LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    public Vehicle(VehicleId id,
                   VehicleModel model,
                   VehicleBrand brand,
                   VehicleYear year,
                   VehicleType type,
                   VehicleColor color,
                   VehicleFuel fuelType,
                   VehicleFuelMeasurement fuelMeasurement,
                   VehicleFuelConsumption fuelConsumption,
                   VehicleAxles cantAxles,
                   VehicleSeats cantSeats,
                   VehicleLoad load,
                   boolean hasTrailer,
                   VehicleStatus status,
                   Coordinates coordinates,
                   LocalDateTime dateCreated,
                   LocalDateTime dateUpdated) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.year = year;
        this.type = type;
        this.color = color;
        this.fuelType = fuelType;
        this.fuelMeasurement = fuelMeasurement;
        this.fuelConsumption = fuelConsumption;
        this.cantAxles = cantAxles;
        this.cantSeats = cantSeats;
        this.load = load;
        this.hasTrailer = hasTrailer;
        this.status = status;
        this.coordinates = coordinates;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    public static Vehicle create(String id,
                                 String model,
                                 String brand,
                                 Integer year,
                                 String type,
                                 String color,
                                 String fuelType,
                                 String fuelMeasurement,
                                 Integer fuelConsumption,
                                 Integer cantAxles,
                                 Integer cantSeats,
                                 Integer load,
                                 boolean hasTrailer,
                                 Double latitude,
                                 Double longitude) {
        final String initialStatus = "AVAILABLE";

        return build(id,
                     model,
                     brand,
                     year,
                     type,
                     color,
                     fuelType,
                     fuelMeasurement,
                     fuelConsumption,
                     cantAxles,
                     cantSeats,
                     load,
                     hasTrailer,
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
                                String type,
                                String color,
                                String fuelType,
                                String fuelMeasurement,
                                Integer fuelConsumption,
                                Integer cantAxles,
                                Integer cantSeats,
                                Integer load,
                                boolean hasTrailer,
                                String status,
                                Double latitude,
                                Double longitude,
                                LocalDateTime dateCreated,
                                LocalDateTime dateUpdated) {
        return new Vehicle(new VehicleId(id),
                new VehicleModel(model),
                new VehicleBrand(brand),
                new VehicleYear(year),
                VehicleType.parse(type),
                new VehicleColor(color.toUpperCase()),
                VehicleFuel.parse(fuelType),
                VehicleFuelMeasurement.parse(fuelMeasurement),
                new VehicleFuelConsumption(fuelConsumption),
                new VehicleAxles(cantAxles),
                new VehicleSeats(cantSeats),
                new VehicleLoad(load),
                hasTrailer,
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

    public void updateModel(String model) {
        this.model = new VehicleModel(model);
        this.dateUpdated = LocalDateTime.now();
    }

    public VehicleYear year() {
        return year;
    }

    public void updateYear(Integer year) {
        this.year = new VehicleYear(year);
        this.dateUpdated = LocalDateTime.now();
    }

    public VehicleBrand brand() {
        return brand;
    }

    public void updateBrand(String brand) {
        this.brand = new VehicleBrand(brand);
        this.dateUpdated = LocalDateTime.now();
    }

    public VehicleType type() {
        return type;
    }
    public void updateType(String type){
        this.type = VehicleType.parse(type);
        this.dateUpdated = LocalDateTime.now();
    }

    public VehicleColor color() {
        return color;
    }
    public void updateColor(String color) {
        this.color = new VehicleColor(color);
        this.dateUpdated = LocalDateTime.now();
    }

    public VehicleFuel fuelType() {
        return fuelType;
    }
    public void updateFuelType(String fuelType) {
        this.fuelType = VehicleFuel.parse(fuelType);
        this.dateUpdated = LocalDateTime.now();
    }

    public VehicleFuelMeasurement fuelMeasurement() {
        return fuelMeasurement;
    }
    public void updateFuelMeasurement(String fuelMeasurement) {
        this.fuelMeasurement = VehicleFuelMeasurement.parse(fuelMeasurement);
        this.dateUpdated = LocalDateTime.now();
    }

    public VehicleFuelConsumption fuelConsumption() {
        return fuelConsumption;
    }
    public void updateFuelConsumption(Integer fuelConsumption) {
        this.fuelConsumption = new VehicleFuelConsumption(fuelConsumption);
        this.dateUpdated = LocalDateTime.now();
    }

    public VehicleAxles cantAxles() {
        return cantAxles;
    }
    public void updateAxles(Integer cantAxles) {
        this.cantAxles = new VehicleAxles(cantAxles);
        this.dateUpdated = LocalDateTime.now();
    }

    public VehicleSeats cantSeats() {
        return cantSeats;
    }
    public void updateSeats(Integer cantSeats) {
        this.cantSeats = new VehicleSeats(cantSeats);
        this.dateUpdated = LocalDateTime.now();
    }

    public VehicleLoad load() {
        return load;
    }
    public void updateLoad(Integer load) {
        this.load = new VehicleLoad(load);
        this.dateUpdated = LocalDateTime.now();
    }

    public boolean hasTrailer() {
        return hasTrailer;
    }
    public void updateHasTrailer(boolean hasTrailer) {
        this.hasTrailer = hasTrailer;
        this.dateUpdated = LocalDateTime.now();
    }

    public VehicleStatus status() {
        return status;
    }

    public void unavailable(){
        this.status = VehicleStatus.UNAVAILABLE;
        this.dateUpdated = LocalDateTime.now();
    }

    public void available() {
        this.status = VehicleStatus.AVAILABLE;
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

    public void updateCoordinates(Double latitude, Double longitude) {
        this.coordinates = new Coordinates(latitude, longitude);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return hasTrailer == vehicle.hasTrailer && Objects.equals(id, vehicle.id) && Objects.equals(model, vehicle.model) && Objects.equals(brand, vehicle.brand) && Objects.equals(year, vehicle.year) && Objects.equals(color, vehicle.color) && fuelType == vehicle.fuelType && Objects.equals(fuelMeasurement, vehicle.fuelMeasurement) && Objects.equals(fuelConsumption, vehicle.fuelConsumption) && Objects.equals(cantAxles, vehicle.cantAxles) && Objects.equals(cantSeats, vehicle.cantSeats) && Objects.equals(load, vehicle.load) && status == vehicle.status && Objects.equals(coordinates, vehicle.coordinates) && Objects.equals(dateCreated, vehicle.dateCreated) && Objects.equals(dateUpdated, vehicle.dateUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, brand, year, color, fuelType, fuelMeasurement, fuelConsumption, cantAxles, cantSeats, load, hasTrailer, status, coordinates, dateCreated, dateUpdated);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", model=" + model +
                ", brand=" + brand +
                ", year=" + year +
                ", color=" + color +
                ", fuelType=" + fuelType +
                ", fuelMeasurement=" + fuelMeasurement +
                ", fuelConsumption=" + fuelConsumption +
                ", cantAxles=" + cantAxles +
                ", cantSeats=" + cantSeats +
                ", load=" + load +
                ", hasTrailer=" + hasTrailer +
                ", status=" + status +
                ", coordinates=" + coordinates +
                ", dateCreated=" + dateCreated +
                ", dateUpdated=" + dateUpdated +
                '}';
    }
}
