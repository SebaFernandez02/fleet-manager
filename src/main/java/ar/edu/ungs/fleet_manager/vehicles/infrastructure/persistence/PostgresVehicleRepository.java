package ar.edu.ungs.fleet_manager.vehicles.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.vehicles.domain.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public final class PostgresVehicleRepository implements VehicleRepository, RowMapper<Vehicle> {
    private final JdbcTemplate jdbcTemplate;

    public PostgresVehicleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Vehicle vehicle) {
        this.findById(vehicle.id())
                .ifPresentOrElse(x -> update(vehicle), () -> create(vehicle));
    }

    private void update(Vehicle vehicle){
        var sql = """
           update vehicles SET
                model = ?, 
                brand = ?, 
                year = ?,
                status = ?, 
                latitude = ?, 
                longitude = ?, 
                date_updated = ?, 
                color = ?, 
                fuel_type = ?, 
                fuel_measurement = ?, 
                fuel_consumption = ?, 
                axles = ?, 
                seats = ?, 
                load = ?, 
                has_trailer = ?,
                type = ?
           where id = ?
           """;
        this.jdbcTemplate.update(sql,
                vehicle.model().value(),
                vehicle.brand().value(),
                vehicle.year().value(),
                vehicle.status().name(),
                vehicle.coordinates().latitude(),
                vehicle.coordinates().longitude(),
                vehicle.dateUpdated(),
                vehicle.color().value(),
                vehicle.fuelType().name(),
                vehicle.fuelMeasurement().name(),
                vehicle.fuelConsumption().value(),
                vehicle.cantAxles().value(),
                vehicle.cantSeats().value(),
                vehicle.load().value(),
                vehicle.hasTrailer(),
                vehicle.type().name(),
                vehicle.id().value());
    }

    private void create(Vehicle vehicle) {
        var sql = """
                    insert into vehicles (id, status, model, brand, year, latitude, longitude, date_created, date_updated color, fuel_type, fuel_measurement, fuel_consumption, axles, seats, load, has_trailer, type) 
                    values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """;
        this.jdbcTemplate.update(sql, vehicle.id().value(),
                vehicle.status().name(),
                vehicle.model().value(),
                vehicle.brand().value(),
                vehicle.year().value(),
                vehicle.coordinates().latitude(),
                vehicle.coordinates().longitude(),
                vehicle.dateCreated(),
                vehicle.dateUpdated(),
                vehicle.color().value(),
                vehicle.fuelType().name(),
                vehicle.fuelMeasurement().name(),
                vehicle.fuelConsumption().value(),
                vehicle.cantAxles().value(),
                vehicle.cantSeats().value(),
                vehicle.load().value(),
                vehicle.hasTrailer(),
                vehicle.type().name());
    }

    @Override
    public Optional<Vehicle> findById(VehicleId id) {
        try {
            var sql = """
                select 
                    id, 
                    status, 
                    model, 
                    brand, 
                    year,
                    latitude, 
                    longitude, 
                    date_created, 
                    date_updated, 
                    color,
                    fuel_type,
                    fuel_measurement,
                    fuel_consumption,
                    axles,
                    seats,
                    load,
                    has_trailer,
                    type
                from vehicles v
                where v.id = ?
            """;

            Vehicle result = this.jdbcTemplate.queryForObject(sql, this, id.value());

            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Vehicle> searchAll() {
        try {
            var sql = """
                select
                    id,
                    status,
                    model,
                    brand,
                    year,
                    latitude,
                    longitude,
                    date_created,
                    date_updated,
                    color,
                    fuel_type,
                    fuel_measurement,
                    fuel_consumption,
                    axles,
                    seats,
                    load,
                    has_trailer,
                    type
                from vehicles v
            """;

            return this.jdbcTemplate.query(sql, this);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Vehicle> searchAllByModel(VehicleBrand brand, VehicleModel model, VehicleYear year) {
        try {
            var sql = """
                select 
                    id, 
                    status, 
                    model, 
                    brand,
                    year,
                    latitude, 
                    longitude, 
                    date_created, 
                    date_updated,
                    color,
                    fuel_type,
                    fuel_measurement,
                    fuel_consumption,
                    axles,
                    seats,
                    load,
                    has_trailer,
                    type 
                from vehicles v
                where brand = ? and model = ? and year = ? 
            """;

            return this.jdbcTemplate.query(sql, this, brand.value(), model.value(), year.value());
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
        var id = rs.getString("id");
        var status = rs.getString("status");
        var model = rs.getString("model");
        var brand = rs.getString("brand");
        var year = rs.getInt("year");
        var color = rs.getString("color");
        var fuelType = rs.getString("fuel_type");
        var fuelMeasurement = rs.getString("fuel_measurement");
        var fuelConsumption = rs.getInt("fuel_consumption");
        var axles = rs.getInt("axles");
        var seats = rs.getInt("seats");
        var load = rs.getInt("load");
        var hasTrailer = rs.getBoolean("has_trailer");
        var latitude = rs.getDouble("latitude");
        var longitude = rs.getDouble("longitude");
        var dateCreated = rs.getTimestamp("date_created").toLocalDateTime();
        var dateUpdated = rs.getTimestamp("date_updated").toLocalDateTime();
        var type = rs.getString("type");

        return Vehicle.build(id, model, brand, year, type,color, fuelType, fuelMeasurement, fuelConsumption, axles, seats, load, hasTrailer, status, latitude, longitude, dateCreated, dateUpdated);
    }
}
