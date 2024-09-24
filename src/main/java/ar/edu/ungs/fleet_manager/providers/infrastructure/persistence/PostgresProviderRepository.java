package ar.edu.ungs.fleet_manager.providers.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.providers.domain.Provider;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderId;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderRepository;
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
public final class PostgresProviderRepository implements ProviderRepository, RowMapper<Provider> {
    private final JdbcTemplate jdbcTemplate;

    public PostgresProviderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Provider provider) {
        var sql = """
                    insert into providers (id, name, email, cuit, phone_number, address)
                    values (CAST(? as UUID), ?, ?, ?, ?, ?)
                  """;
        this.jdbcTemplate.update(sql,
                provider.id().value(),
                provider.name().value(),
                provider.email().value(),
                provider.cuit().value(),
                provider.phoneNumber().value(),
                provider.address().value());
    }

    @Override
    public Optional<Provider> findById(ProviderId id) {
        try {
            var sql = """
                select 
                id, 
                name, 
                email, 
                cuit, 
                phone_number, 
                address
                from providers p
                where p.id = CAST(? as UUID)
            """;

            Provider result = this.jdbcTemplate.queryForObject(sql, this, id.value());

            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Provider> searchAll() {
        try {
            var sql = """
                select
                    id,
                    name,
                    email,
                    cuit,
                    phone_number,
                    address
                from providers p
            """;

            return this.jdbcTemplate.query(sql, this);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Provider mapRow(ResultSet rs, int rowNum) throws SQLException {
        var id = rs.getString("id");
        var name = rs.getString("name");
        var email = rs.getString("email");
        var cuit = rs.getString("cuit");
        var phoneNumber = rs.getString("phone_number");
        var address = rs.getString("address");

        return Provider.build(id, name, email, cuit, phoneNumber, address);
    }
}
