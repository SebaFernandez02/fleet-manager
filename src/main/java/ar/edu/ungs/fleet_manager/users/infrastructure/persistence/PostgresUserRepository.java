package ar.edu.ungs.fleet_manager.users.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import ar.edu.ungs.fleet_manager.users.domain.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
                    insert into users (id, username, password, full_name, enterprise_id, date_created, date_updated)
                    values (CAST(? as UUID), ?, ?, ?, CAST(? as UUID), ?, ?)
                """;

        this.jdbcTemplate.update(sql,
                                 user.id().value(),
                                 user.username().value(),
                                 user.password().value(),
                                 user.fullName().value(),
                                 user.enterpriseId().map(EnterpriseId::value).orElse(null),
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
                    u.enterprise_id,
                    u.date_created, 
                    u.date_updated
                from users u
                    left join user_roles r on r.user_id = u.id 
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
                    u.enterprise_id,
                    u.date_created, 
                    u.date_updated
                from users u
                    left join user_roles r on r.user_id = u.id 
                where u.username = ?
            """;

            var result = this.jdbcTemplate.queryForObject(sql, this, username.value());

            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> searchAll(EnterpriseId enterpriseId) {
        try {
            var args = new ArrayList<Object>();

            var sql = """
                select
                    u.id, 
                    u.username, 
                    u.password, 
                    u.full_name,
                    r.role,
                    u.enterprise_id,
                    u.date_created, 
                    u.date_updated
                from users u
                    left join user_roles r on r.user_id = u.id
            """;

            if (enterpriseId != null) {
                sql = sql + "where u.enterprise_id = CAST(? AS UUID)";
                args.add(enterpriseId.value());
            } else {
                sql = sql + "where u.enterprise_id is null";
            }

            return this.jdbcTemplate.query(sql, rs -> {
                Map<String, UserMapper> userMap = new HashMap<>();

                while (rs.next()) {
                    String userId = rs.getString("id");

                    UserMapper user = userMap.get(userId);
                    if (user == null) {
                        user = new UserMapper(
                                userId,
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getString("full_name"),
                                new ArrayList<>(),
                                rs.getString("enterprise_id"),
                                rs.getTimestamp("date_created").toLocalDateTime(),
                                rs.getTimestamp("date_updated").toLocalDateTime()
                        );
                        userMap.put(userId, user);
                    }

                    String role = rs.getString("role");
                    if (role != null) {
                        user.getRoles().add(role);
                    }
                }

                return userMap.values().stream().map(x -> User.build(x.getId(),
                        x.getRoles(),
                        x.getUsername(),
                        x.getPassword(),
                        x.getFullName(),
                        x.getEnterpriseId(),
                        x.getDateCreated(),
                        x.getDateUpdated())).collect(Collectors.toList());
            }, args.toArray());
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        String id = rs.getString("id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String fullName = rs.getString("full_name");
        String enterpriseId = rs.getString("enterprise_id");
        LocalDateTime dateCreated = rs.getTimestamp("date_created").toLocalDateTime();
        LocalDateTime dateUpdated = rs.getTimestamp("date_updated").toLocalDateTime();

        List<String> roles = new ArrayList<>();

        do {
            String role = rs.getString("role");

            if (role != null)
                roles.add(rs.getString("role"));
        } while (rs.next());

        return User.build(id, roles, username, password, fullName, enterpriseId, dateCreated, dateUpdated);
    }
}
