package ar.edu.ungs.fleet_manager.enterprises.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.enterprises.domain.Enterprise;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseRepository;
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
public class PostgresEnterpriseRepository implements EnterpriseRepository, RowMapper<Enterprise> {
    private final JdbcTemplate jdbcTemplate;

    public PostgresEnterpriseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Enterprise enterprise) {
        this.findById(enterprise.id()).ifPresentOrElse(this::update, () -> this.create(enterprise));
    }

    private void create(Enterprise enterprise) {
        var sql = """
                    insert into enterprises(id, name)
                    values(CAST(? as UUID), ?, )
                """;
        this.jdbcTemplate.update(sql, enterprise.id().value(), enterprise.name().value());
    }

    private void update(Enterprise enterprise) {
        var sql = """
                UPDATE enterprises
                SET name = ?
                WHERE id = CAST(? as UUID)
            """;
        this.jdbcTemplate.update(sql, enterprise.name().value(), enterprise.id().value());
    }

    @Override
    public Optional<Enterprise> findById(EnterpriseId id) {
        try{
            var sql = """
                        select * from enterprises where id = CAST(? as UUID)
                    """;

            var result = this.jdbcTemplate.queryForObject(sql, this, id.value());

            return Optional.ofNullable(result);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Enterprise> searchAll() {
        try{
            var sql = """
                        select * from enterprises
                    """;

            return this.jdbcTemplate.query(sql, this);
        } catch(EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Enterprise mapRow(ResultSet rs, int rowNum) throws SQLException {
        var id = rs.getString("id");
        var name = rs.getString("name");

        return Enterprise.build(id, name);
    }
}
