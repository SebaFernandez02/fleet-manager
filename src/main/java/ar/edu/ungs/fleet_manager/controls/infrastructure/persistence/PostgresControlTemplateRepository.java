package ar.edu.ungs.fleet_manager.controls.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.controls.domain.Control;
import ar.edu.ungs.fleet_manager.controls.domain.ControlId;
import ar.edu.ungs.fleet_manager.controls.domain.ControlTemplate;
import ar.edu.ungs.fleet_manager.controls.domain.ControlTemplateRepository;
import ar.edu.ungs.fleet_manager.shared.infrastructure.persistence.PostgresException;
import org.springframework.core.NestedRuntimeException;
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
public class PostgresControlTemplateRepository implements ControlTemplateRepository, RowMapper<ControlTemplate> {
    private final JdbcTemplate jdbcTemplate;

    public PostgresControlTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(ControlTemplate controlTemplate) {
        try{
            var sql= """
                    insert into control_template (id, subject, description, priority, assigned) values 
                                         (CAST(? as UUID), ?, ?, ?, ?)
                    """;

            this.jdbcTemplate.update(sql,
                    controlTemplate.id().value(),
                    controlTemplate.subject().value(),
                    controlTemplate.description().value(),
                    controlTemplate.priority().name(),
                    controlTemplate.assigned().value());

        } catch (NestedRuntimeException e) {
            throw new PostgresException(e);
        }
    }

    @Override
    public Optional<ControlTemplate> findById(ControlId id) {
        try {
            var sql = "select * from control_template where id = CAST(? as UUID)";

            ControlTemplate result = jdbcTemplate.queryForObject(sql,this, id.value());

            return Optional.ofNullable(result);
        }catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<ControlTemplate> searchAll() {
        try {
            var sql = "select * from control_template";

            return this.jdbcTemplate.query(sql, this);

        }catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public ControlTemplate mapRow(ResultSet rs, int rowNum) throws SQLException {
        try{
            var id = rs.getString("id");
            var subject = rs.getString("subject");
            var description = rs.getString("description");
            var priority = rs.getString("priority");
            var assigned = rs.getString("assigned");

            return ControlTemplate.build(id, subject, description, priority, assigned);
        }catch (SQLException e){
            throw new PostgresException(e.getMessage());
        }
    }
}
