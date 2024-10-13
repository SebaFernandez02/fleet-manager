package ar.edu.ungs.fleet_manager.controls.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.controls.domain.Control;
import ar.edu.ungs.fleet_manager.controls.domain.ControlId;
import ar.edu.ungs.fleet_manager.controls.domain.ControlRepository;
import ar.edu.ungs.fleet_manager.controls.domain.ControlType;
import ar.edu.ungs.fleet_manager.orders.domain.Order;
import ar.edu.ungs.fleet_manager.shared.infrastructure.persistence.PostgresException;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataAccessException;
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
public class PostgresControlRepository implements ControlRepository, RowMapper<Control> {
    private final JdbcTemplate jdbcTemplate;

    public PostgresControlRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Control control) {
        this.findById(control.id()).ifPresentOrElse(x -> update(control), () -> create(control));

    }

    private void update(Control control) {
        try {
            var sql = "update controls set status = ?, id_operator = CAST(? as UUID) where id = CAST(? as UUID)";

            this.jdbcTemplate.update(sql, control.status().name(), control.operatorId().value(), control.id().value());

        }catch(DataAccessException e){
            throw new PostgresException(e);
        }
    }

    private void create(Control control) {
        try{
            var sql= """
                    insert into controls (id, type, subject, description, id_vehicle, priority, date, status, id_operator) values 
                                         (CAST(? as UUID), ?, ?, ?, ?, ?, ?, ?, CAST(? as UUID))
                    """;

            this.jdbcTemplate.update(sql,
                                     control.id().value(),
                                     control.type().name(),
                                     control.subject().value(),
                                     control.description().value(),
                                     control.vehicleId().value(),
                                     control.priority().name(),
                                     control.date(),
                                     control.status().name(),
                                     control.operatorId().value());
        } catch (NestedRuntimeException e) {
            throw new PostgresException(e);
        }
    }

    @Override
    public Optional<Control> findById(ControlId id){
        try {
            var sql = "select * from controls where id = CAST(? as UUID)";

            Control result = jdbcTemplate.queryForObject(sql,this, id.value());

            return Optional.ofNullable(result);
        }catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Control> searchAll() {
        try {
            var sql = "select * from controls order by date desc";

            return this.jdbcTemplate.query(sql, this);

        }catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

//    @Override
//    public Optional<Order> findByType(ControlType type) {
//        return Optional.empty();
//    }

    @Override
    public Control mapRow(ResultSet rs, int rowNum) throws SQLException {
        try{
            var id = rs.getString("id");
            var type = rs.getString("type");
            var subject = rs.getString("subject");
            var description = rs.getString("description");
            var vehicleId = rs.getString("id_vehicle");
            var priority = rs.getString("priority");
            var date = rs.getTimestamp("date").toLocalDateTime();
            var status = rs.getString("status");
            var operatorId = rs.getString("id_operator");

            return Control.build(id, type, subject, description, vehicleId, priority, date, status, operatorId);
        }catch (SQLException e){
            throw new PostgresException(e.getMessage());
        }
    }
}
