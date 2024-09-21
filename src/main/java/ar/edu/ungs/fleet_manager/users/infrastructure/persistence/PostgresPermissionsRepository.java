package ar.edu.ungs.fleet_manager.users.infrastructure.persistence;

import ar.edu.ungs.fleet_manager.users.domain.*;
import ar.edu.ungs.fleet_manager.users.domain.Module;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public final class PostgresPermissionsRepository implements PermissionsRepository, RowMapper<Permissions> {
    private final JdbcTemplate jdbcTemplate;

    public PostgresPermissionsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Permissions findByRoles(Role... roles) {
        try {
            String placeholders = String.join(",", Collections.nCopies(roles.length, "?"));

            var sql = String.format("""
                select
                    module,
                    operation
                from permissions p
                where p.id in (select distinct permission_id from role_permissions rp where rp.role in (%s))
            """, placeholders);

            var args = Arrays.stream(roles).map(Role::name).toArray();

            var values = this.jdbcTemplate.query(sql, this, args);

            return values.isEmpty() ? new Permissions(Collections.emptySet()) : values.getFirst();
        } catch (EmptyResultDataAccessException e) {
            return new Permissions(Collections.emptySet());
        }
    }

    @Override
    public Permissions mapRow(ResultSet rs, int rowNum) throws SQLException {
        Set<Permission> permissions = new HashSet<>();
        Map<Module, Set<Operation>> values = new HashMap<>();

        while (rs.next()) {
            var module = Module.parse(rs.getString("module"));

            if (!values.containsKey(module)) {
                values.put(module, new HashSet<>());
            }

            var operation = Operation.parse(rs.getString("operation"));

            values.get(module).add(operation);
        }


        values.keySet().forEach(module -> permissions.add(new Permission(module, values.get(module))));

        return new Permissions(permissions);
    }
}
