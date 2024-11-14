package ar.edu.ungs.fleet_manager.configs.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.configs.domain.ApiKey;
import ar.edu.ungs.fleet_manager.configs.domain.ApiKeyRepository;
import ar.edu.ungs.fleet_manager.configs.domain.ApiKeyType;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import ar.edu.ungs.fleet_manager.shared.infrastructure.persistence.PostgresException;
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
public final class PostgresApiKeyRespository implements ApiKeyRepository, RowMapper<ApiKey> {
    private final JdbcTemplate jdbcTemplate;

    public PostgresApiKeyRespository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(ApiKey apiKey) {
        this.findByType(apiKey.type(), apiKey.enterpriseId()).ifPresentOrElse(x -> update(apiKey), () -> create(apiKey));
    }

    private void create(ApiKey apiKey) {
        try {
            var sql = """
                      insert into api_keys (type, key, secret, enterprise_id)
                      values (?, ?, ?, CAST(? as UUID))
                    """;

            this.jdbcTemplate.update(sql, apiKey.type().name(), apiKey.key(), apiKey.secret(), apiKey.enterpriseId().value());
        } catch (Exception e) {
            throw new PostgresException(e.getMessage());
        }

    }

    private void update(ApiKey apiKey) {

        var sql = """
                  update api_keys set
                   key = ?, secret = ?
                  where enterprise_id = CAST(? AS UUID) and type = ?
                """;
        try {


            this.jdbcTemplate.update(sql,apiKey.key(), apiKey.secret(), apiKey.enterpriseId().value(), apiKey.type().name());
        } catch (Exception e) {
            throw new PostgresException(e.getMessage());
        }

    }

    @Override
    public Optional<ApiKey> findByType(ApiKeyType type, EnterpriseId enterpriseId) {
        try {
            var sql = """
                        select *
                        from api_keys ak
                        where ak.type = ? and enterprise_id = CAST(? as UUID)
                    """;

            ApiKey result = this.jdbcTemplate.queryForObject(sql, this, type.name(), enterpriseId.value());

            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<ApiKey> searchAll(EnterpriseId enterpriseId) {
        try {
            var sql = """
                        select *
                        from api_keys ak
                        where ak.enterprise_id = CAST(? AS UUID)
                        order by date_updated desc, date_created desc
                    """;

            return this.jdbcTemplate.query(sql, this, enterpriseId.value());
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<ApiKey> findBySecret(EnterpriseId enterpriseId) {
        try {
            var sql = """
                        select *
                        from api_keys ak
                        where ak.secret = false and enterprise_id = CAST(? as UUID)
                    """;

            ApiKey result = this.jdbcTemplate.queryForObject(sql, this, enterpriseId.value());

            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public ApiKey mapRow(ResultSet rs, int rowNum) throws SQLException {
        var type = rs.getString("type");
        var key = rs.getString("key");
        var secret = rs.getBoolean("secret");
        var enterpriseId = rs.getString("enterprise_id");

        return ApiKey.create(type, key, secret, enterpriseId);
    }
}
