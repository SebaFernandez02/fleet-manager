package ar.edu.ungs.fleet_manager.users.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.users.domain.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public final class PostgresUserRepository implements UserRepository, RowMapper<User> {
    private final JdbcTemplate jdbcTemplate;

    public PostgresUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(User user) {
        this.findById(user.id()).ifPresentOrElse(ignored -> update(user), () -> create(user));
    }

    private void create(User user) {
        var sql = """
                    insert into users (id, username, password, full_name, date_created, date_updated)
                    values (CAST(? as UUID), ?, ?, ?, ?, ?)
                """;

        this.jdbcTemplate.update(sql,
                                 user.id().value(),
                                 user.username().value(),
                                 user.password().value(),
                                 user.fullName().value(),
                                 user.dateCreated(),
                                 user.dateUpdated());

        this.saveRoles(user);
    }

    private void update(User user) {
        var sql = """
                    update users set id = CAST(? as UUID), username = ?, password = ?, full_name = ?, date_created = ?, date_updated = ?
                    where id = CAST(? as UUID)
                """;

        this.jdbcTemplate.update(sql,
                                 user.id().value(),
                                 user.username().value(),
                                 user.password().value(),
                                 user.fullName().value(),
                                 user.dateCreated(),
                                 user.dateUpdated(),
                                 user.id().value());

        this.saveRoles(user);
    }

    private void saveRoles(User user) {
        var sql = """
                insert into user_roles (user_id, role) 
                values (CAST(? as UUID), ?)
                """;

        user.roles().stream().filter(role -> !containsRole(user.id(), role)).forEach(role -> this.jdbcTemplate.update(sql, user.id().value(), role.name()));
    }

    private boolean containsRole(UserId id, Role role) {
        try {
            var sql = """
                    select 1 
                    from user_roles 
                    where user_id = CAST(? as UUID) and role = ?
                """;

            this.jdbcTemplate.queryForObject(sql, Integer.class, id.value(), role.name());

            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public Optional<User> findById(UserId id) {
        try {
            var sql = """
                select
                    u.id, 
                    u.username, 
                    u.password, 
                    u.full_name,
                    r.role, 
                    u.date_created, 
                    u.date_updated
                from users u
                    inner join user_roles r on r.user_id = u.id 
                where u.id = CAST(? as UUID)
            """;

            var result = this.jdbcTemplate.queryForObject(sql, this, id.value());

            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByUsername(Username username) {
        try {
            var sql = """
                select
                    u.id, 
                    u.username, 
                    u.password, 
                    u.full_name,
                    r.role,
                    u.date_created, 
                    u.date_updated
                from users u
                    inner join user_roles r on r.user_id = u.id 
                where u.username = ?
            """;

            var result = this.jdbcTemplate.queryForObject(sql, this, username.value());

            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> searchAll() {
        try {
            var sql = """
                select
                    u.id, 
                    u.username, 
                    u.password, 
                    u.full_name,
                    r.role,
                    u.date_created, 
                    u.date_updated
                from users u
                    inner join user_roles r on r.user_id = u.id
            """;

            return this.jdbcTemplate.query(sql, this);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        String id = null;
        String username = null;
        String password = null;
        String fullName = null;
        LocalDateTime dateCreated = null;
        LocalDateTime dateUpdated = null;
        List<String> roles = new ArrayList<>();

        while (rs.next()) {
            if (id == null) {
                id = rs.getString("id");
                username = rs.getString("username");
                password = rs.getString("password");
                fullName = rs.getString("full_name");
                dateCreated = rs.getTimestamp("date_created").toLocalDateTime();
                dateUpdated = rs.getTimestamp("date_updated").toLocalDateTime();
            }

            roles.add(rs.getString("role"));
        }

        return User.build(id, roles, username, password, fullName, dateCreated, dateUpdated);
    }
}
