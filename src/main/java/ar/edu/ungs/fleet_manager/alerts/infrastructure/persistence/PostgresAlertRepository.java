package ar.edu.ungs.fleet_manager.alerts.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.alerts.domain.Alert;
import ar.edu.ungs.fleet_manager.alerts.domain.AlertId;
import ar.edu.ungs.fleet_manager.alerts.domain.AlertRepository;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import ar.edu.ungs.fleet_manager.products.domain.Product;
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
public final class PostgresAlertRepository implements AlertRepository, RowMapper<Alert> {
    private final JdbcTemplate jdbcTemplate;

    public PostgresAlertRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Alert alert) {
        this.findById(alert.id()).ifPresentOrElse(this::update, () -> this.create(alert));
    }

    public void create(Alert alert) {
        var sql = """
                    insert into alerts(id, priority, title, description, acknowledge, enterprise_id, date_created, date_updated)
                    values(CAST(? as UUID), ?, ?, ?, ?, CAST(? as UUID), ?, ?)
                """;
        this.jdbcTemplate.update(sql, alert.id().value(),
                                      alert.priority().name(),
                                      alert.title().value(),
                                      alert.description().value(),
                                      alert.acknowledge(),
                                      alert.enterpriseId().value(),
                                      alert.dateCreated(),
                                      alert.dateUpdated());
    }

    public void update(Alert alert) {
        String sql = """
                        update alerts set acknowledge = ?, date_updated = ? 
                        where id = CAST(? as UUID)
                    """;
        this.jdbcTemplate.update(sql, alert.acknowledge(), alert.dateUpdated(), alert.id().value()
        );
    }

    @Override
    public Optional<Alert> findById(AlertId id) {
        try {
            var sql = """
                        select * from alerts where id = CAST(? as UUID)
                    """;
            Alert result = this.jdbcTemplate.queryForObject(sql, this, id.value());
            return Optional.ofNullable(result);

        } catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Alert> searchAll(EnterpriseId enterpriseId) {
        try {

            var sql = """
                        SELECT * FROM alerts where enterprise_id = CAST(? as UUID)
                    """;
            return this.jdbcTemplate.query(sql, this, enterpriseId.value());

        } catch (EmptyResultDataAccessException e){
            return Collections.emptyList();
        }
    }

    @Override
    public Alert mapRow(ResultSet rs, int rowNum) throws SQLException {
        var id = rs.getString("id");
        var priority = rs.getString("priority");
        var title = rs.getString("title");
        var description = rs.getString("description");
        var acknowledge = rs.getBoolean("acknowledge");
        var enterpriseId = rs.getString("enterprise_id");
        var dateCreated = rs.getTimestamp("date_created").toLocalDateTime();
        var dateUpdated = rs.getTimestamp("date_updated").toLocalDateTime();

        return Alert.build(id, priority, title, description, acknowledge, enterpriseId, dateCreated, dateUpdated);
    }
}
