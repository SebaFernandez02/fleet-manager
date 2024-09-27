package ar.edu.ungs.fleet_manager.reserves.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.reserves.domain.*;
import ar.edu.ungs.fleet_manager.users.domain.UserId;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
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
public final class PostgresReserveRepository implements ReserveRepository, RowMapper<Reserve> {
    private final JdbcTemplate jdbcTemplate;

    public PostgresReserveRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Reserve reserve) {
        this.findById(reserve.id())
                .ifPresentOrElse(x -> this.update(reserve), () -> this.create(reserve));
    }

    private void update(Reserve reserve) {
        var sql = """
            update reserves 
            set status = ?, vehicle_id = ?, user_id = CAST(? as UUID), latitude_from = ?, longitude_from = ?, latitude_to = ?, longitude_to = ?, date_created = ?, date_updated = ?
            where id = CAST(? as UUID)
          """;
        this.jdbcTemplate.update(sql,
                reserve.status().name(),
                reserve.vehicleId().value(),
                reserve.userId().value(),
                reserve.trip().from().latitude(),
                reserve.trip().from().longitude(),
                reserve.trip().to().latitude(),
                reserve.trip().to().longitude(),
                reserve.dateCreated(),
                reserve.dateUpdated(),
                reserve.id().value());

    }

    private void create(Reserve reserve) {
        var sql = """
                    insert into reserves (id, status, vehicle_id, user_id, latitude_from, longitude_from, latitude_to, longitude_to, date_created, date_updated) 
                    values (CAST(? as UUID), ?, ?, CAST(? as UUID), ?, ?, ?, ?, ?, ?)
                    """;
        this.jdbcTemplate.update(sql, reserve.id().value(),
                                      reserve.status().name(),
                                      reserve.vehicleId().value(),
                                      reserve.userId().value(),
                                      reserve.trip().from().latitude(),
                                      reserve.trip().from().longitude(),
                                      reserve.trip().to().latitude(),
                                      reserve.trip().to().longitude(),
                                      reserve.dateCreated(),
                                      reserve.dateUpdated());
    }

    @Override
    public Optional<Reserve> findById(ReserveId id) {
        try {
            var sql = """
                select id, status, vehicle_id, user_id, latitude_from, longitude_from, latitude_to, longitude_to, date_created, date_updated
                from reserves r
                where r.id = CAST(? as UUID)
            """;

            var result = this.jdbcTemplate.queryForObject(sql, this, id.value());

            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Reserve> findByUserId(UserId id) {
        try {
            var sql = """
                select id, status, vehicle_id, user_id, latitude_from, longitude_from, latitude_to, longitude_to, date_created, date_updated
                from reserves r
                where r.user_id = CAST(? as UUID)
            """;

            return this.jdbcTemplate.query(sql, this, id.value());
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Reserve> findByVehicleId(VehicleId id) {
        try {
            var sql = """
                select id, status, vehicle_id, user_id, latitude_from, longitude_from, latitude_to, longitude_to, date_created, date_updated
                from reserves r
                where r.vehicle_id = ?
            """;

            return this.jdbcTemplate.query(sql, this, id.value());
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Reserve> searchAll() {
        try {
            var sql = """
                select id, status, vehicle_id, user_id, latitude_from, longitude_from, latitude_to, longitude_to, date_created, date_updated
                from reserves r
            """;

            return this.jdbcTemplate.query(sql, this);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Reserve mapRow(ResultSet rs, int rowNum) throws SQLException {
        var id = rs.getString("id");
        var status = rs.getString("status");
        var vehicleId = rs.getString("vehicle_id");
        var userId = rs.getString("user_id");
        var dateCreated = rs.getTimestamp("date_created").toLocalDateTime();
        var dateUpdated = rs.getTimestamp("date_updated").toLocalDateTime();
        var latitudeFrom = rs.getDouble("latitude_from");
        var longitudeFrom = rs.getDouble("longitude_from");
        var latitudeTo = rs.getDouble("latitude_to");
        var longitudeTo = rs.getDouble("longitude_to");

        return Reserve.build(id, status, vehicleId, userId, latitudeFrom, longitudeFrom, latitudeTo, longitudeTo, dateCreated, dateUpdated);
    }
}
