package ar.edu.ungs.fleet_manager.configs.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.configs.domain.Config;
import ar.edu.ungs.fleet_manager.configs.domain.ConfigRepository;
import ar.edu.ungs.fleet_manager.configs.domain.ConfigType;
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
public final class PostgresConfigRespository implements ConfigRepository, RowMapper<Config> {
    private final JdbcTemplate jdbcTemplate;

    public PostgresConfigRespository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Config config) {
        this.findByType(config.type(), config.enterpriseId()).ifPresentOrElse(x -> update(config), () -> create(config));
    }

    private void create(Config config) {
        try {
            var sql = """
                      insert into configs (type, key, secret, enterprise_id)
                      values (?, ?, ?, CAST(? as UUID))
                    """;

            this.jdbcTemplate.update(sql, config.type().name(), config.key(), config.secret(), config.enterpriseId().value());
        } catch (Exception e) {
            throw new PostgresException(e.getMessage());
        }

    }

    private void update(Config config) {

        var sql = """
                  update configs set
                   key = ?, secret = ?
                  where enterprise_id = CAST(? AS UUID) and type = ?
                """;
        try {


            this.jdbcTemplate.update(sql, config.key(), config.secret(), config.enterpriseId().value(), config.type().name());
        } catch (Exception e) {
            throw new PostgresException(e.getMessage());
        }

    }

    @Override
    public Optional<Config> findByType(ConfigType type, EnterpriseId enterpriseId) {
        try {
            var sql = """
                        select *
                        from configs ak
                        where ak.type = ? and enterprise_id = CAST(? as UUID)
                    """;

            Config result = this.jdbcTemplate.queryForObject(sql, this, type.name(), enterpriseId.value());

            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Config> searchAll(EnterpriseId enterpriseId) {
        try {
            var sql = """
                        select *
                        from configs ak
                        where ak.enterprise_id = CAST(? AS UUID)
                        order by date_updated desc, date_created desc
                    """;

            return this.jdbcTemplate.query(sql, this, enterpriseId.value());
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Config> findBySecret(EnterpriseId enterpriseId) {
        try {
            var sql = """
                        select *
                        from configs ak
                        where ak.secret = false and enterprise_id = CAST(? as UUID)
                    """;

            Config result = this.jdbcTemplate.queryForObject(sql, this, enterpriseId.value());

            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Config mapRow(ResultSet rs, int rowNum) throws SQLException {
        var type = rs.getString("type");
        var key = rs.getString("key");
        var secret = rs.getBoolean("secret");
        var enterpriseId = rs.getString("enterprise_id");

        return Config.create(type, key, secret, enterpriseId);
    }
}
