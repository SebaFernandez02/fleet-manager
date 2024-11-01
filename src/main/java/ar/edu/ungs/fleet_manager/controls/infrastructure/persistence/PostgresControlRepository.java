package ar.edu.ungs.fleet_manager.controls.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.controls.domain.Control;
import ar.edu.ungs.fleet_manager.controls.domain.ControlId;
import ar.edu.ungs.fleet_manager.controls.domain.ControlRepository;
import ar.edu.ungs.fleet_manager.shared.infrastructure.persistence.PostgresException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper mapper;

    public PostgresControlRepository(JdbcTemplate jdbcTemplate, ObjectMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    @Override
    public void save(Control control) {
        this.findById(control.id()).ifPresentOrElse(x -> update(control), () -> create(control));
    }

    private void update(Control control) {
        try {
            var sql = "update controls set status = ?, operator_id = CAST(? as UUID), date_updated = ?, products = ? where id = CAST(? as UUID)";

            String productsJson = mapper.writeValueAsString(control.products().stream().map(ControlProductDto::map).toList());

            this.jdbcTemplate.update(sql,
                    control.status().name(),
                    control.operatorId() == null ? null : control.operatorId().value(),
                    control.dateUpdated(),
                    productsJson,
                    control.id().value());
        }catch(DataAccessException | JsonProcessingException e){
            throw new PostgresException(e.getMessage());
        }
    }

    private void create(Control control) {
        try{
            var sql= """
                    insert into controls (id, type, subject, description, vehicle_id, priority, status, operator_id, date_created, date_updated, products) values 
                                         (CAST(? as UUID), ?, ?, ?, ?, ?, ?, CAST(? as UUID), ?, ?, ?)
                    """;

            String productsJson = mapper.writeValueAsString(control.products().stream().map(ControlProductDto::map).toList());
            this.jdbcTemplate.update(sql,
                                     control.id().value(),
                                     control.type().name(),
                                     control.subject().value(),
                                     control.description().value(),
                                     control.vehicleId().value(),
                                     control.priority().name(),
                                     control.status().name(),
                                     control.operatorId() == null ? null : control.operatorId().value(),
                                     control.dateCreated(),
                                     control.dateUpdated(),
                                     productsJson);
        } catch (NestedRuntimeException | JsonProcessingException e) {
            throw new PostgresException(e.getMessage());
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
            var sql = "select * from controls order by date_updated desc";

            return this.jdbcTemplate.query(sql, this);

        }catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Control mapRow(ResultSet rs, int rowNum) throws SQLException {
        try{
            var id = rs.getString("id");
            var type = rs.getString("type");
            var subject = rs.getString("subject");
            var description = rs.getString("description");
            var vehicleId = rs.getString("vehicle_id");
            var priority = rs.getString("priority");
            var dateCreated = rs.getTimestamp("date_created").toLocalDateTime();
            var dateUpdated = rs.getTimestamp("date_updated").toLocalDateTime();
            var status = rs.getString("status");
            var operatorId = rs.getString("operator_id");
            var productsJson = rs.getString("products");
            var products = mapper.readValue(productsJson, new TypeReference<List<ControlProductDto>>(){}).stream().map(ControlProductDto::map).toList();


            return Control.build(id, type, status, operatorId, subject, description, vehicleId, priority, dateCreated, dateUpdated,products);
        }catch (SQLException | JsonProcessingException e){
            throw new PostgresException(e.getMessage());
        }
    }
}
