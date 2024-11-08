package ar.edu.ungs.fleet_manager.reserves.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import ar.edu.ungs.fleet_manager.reserves.domain.*;
import ar.edu.ungs.fleet_manager.shared.infrastructure.exceptions.InfrastructureException;
import ar.edu.ungs.fleet_manager.trips.domain.Trip;
import ar.edu.ungs.fleet_manager.users.domain.UserId;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleId;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    public PostgresReserveRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void save(Reserve reserve) {
        this.findById(reserve.id())
                .ifPresentOrElse(x -> this.update(reserve), () -> this.create(reserve));
    }

    private void update(Reserve reserve) {
        try {
            var sql = """
                update reserves 
                set status = ?,
                vehicle_id = ?,
                user_id = CAST(? as UUID),
                trip = ?,
                date_created = ?,
                date_updated = ?,
                date_reserve = ?,
                date_finish_reserve = ?,
                fuel_consumption = ?
                where id = CAST(? as UUID)
              """;

            this.jdbcTemplate.update(sql,
                                     reserve.status().name(),
                                     reserve.vehicleId().value(),
                                     reserve.userId().value(),
                                     this.objectMapper.writeValueAsString(reserve.trip()),
                                     reserve.dateCreated(),
                                     reserve.dateUpdated(),
                                     reserve.dateReserve(),
                                     reserve.dateFinishReserve(),
                                     reserve.fuelConsumption(),
                                     reserve.id().value());
        } catch (JsonProcessingException e) {
            throw new InfrastructureException(e.getMessage());
        }

    }

    private void create(Reserve reserve) {
        try {
            var sql = """
                    insert into reserves (id, status, vehicle_id, user_id, trip, date_created, date_updated, date_reserve, date_finish_reserve, fuel_consumption, enterprise_id) 
                    values (CAST(? as UUID), ?, ?, CAST(? as UUID), ?, ?, ?, ?, ?, ?, CAST(? as UUID))
                    """;
            this.jdbcTemplate.update(sql,
                                     reserve.id().value(),
                                     reserve.status().name(),
                                     reserve.vehicleId().value(),
                                     reserve.userId().value(),
                                     this.objectMapper.writeValueAsString(reserve.trip()),
                                     reserve.dateCreated(),
                                     reserve.dateUpdated(),
                                     reserve.dateReserve(),
                                     reserve.dateFinishReserve(),
                                     reserve.fuelConsumption(),
                                     reserve.enterpriseId().value());
        } catch (JsonProcessingException e) {
            throw new InfrastructureException(e.getMessage());
        }
    }

    @Override
    public Optional<Reserve> findById(ReserveId id) {
        try {
            var sql = """
                select id, status, vehicle_id, user_id, trip, date_created, date_updated, date_reserve, date_finish_reserve, fuel_consumption
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
    public List<Reserve> findByUserId(UserId id, EnterpriseId enterpriseId) {
        try {
            var sql = """
                select *
                from reserves r
                where r.user_id = CAST(? as UUID)
                and enterprise_id = CAST(? as UUID)
            """;

            return this.jdbcTemplate.query(sql, this, id.value(), enterpriseId.value());
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Reserve> findByVehicleId(VehicleId id, EnterpriseId enterpriseId) {
        try {
            var sql = """
                select *
                from reserves r
                where r.vehicle_id = ?
                and enterprise_id = CAST(? as UUID)
            """;

            return this.jdbcTemplate.query(sql, this, id.value(), enterpriseId.value());
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Reserve> searchAll(EnterpriseId enterpriseId) {
        try {
            var sql = """
                select *
                from reserves r
                where enterprise_id = CAST(? AS UUID)
            """;

            return this.jdbcTemplate.query(sql, this, enterpriseId.value());
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Reserve mapRow(ResultSet rs, int rowNum) throws SQLException {
        var id = rs.getString("id");
        var status = rs.getString("status");
        var vehicleId = rs.getString("vehicle_id");
        var enterpriseId = rs.getString("enterprise_id");
        var userId = rs.getString("user_id");
        var dateCreated = rs.getTimestamp("date_created").toLocalDateTime();
        var dateUpdated = rs.getTimestamp("date_updated").toLocalDateTime();
        var trip = this.mapTrip(rs.getString("trip"));
        var dateReserve = rs.getTimestamp("date_reserve").toLocalDateTime();
        var dateFinishReserve = rs.getTimestamp("date_finish_reserve").toLocalDateTime();
        var fuelConsumption = rs.getDouble("fuel_consumption");

        return Reserve.build(id, status, vehicleId, userId, trip, enterpriseId, dateCreated, dateUpdated, dateReserve, dateFinishReserve, fuelConsumption);
    }

    private Trip mapTrip(String trip) {
        try {
            return this.objectMapper.readValue(trip, Trip.class);
        } catch (Exception e) {
            throw new InfrastructureException(e.getMessage());
        }

    }
}
