package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        ValidationUtil.validation(user);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            setRoles(user);
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name, email=:email, password=:password, 
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) == 0) {
            return null;
        }
        return updateRoles(user);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        return getRolesToUser(DataAccessUtils.singleResult(users));
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        return getRolesToUser(DataAccessUtils.singleResult(users));
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        return getRolesAllUsers(users);
    }

    private List<User> getRolesAllUsers(List<User> users) {
        List<Map<String, Object>> roles = jdbcTemplate.queryForList("SELECT * FROM user_roles");
        for (User user : users) {
            Set<Role> roleSet = new HashSet<>();
            for (Map<String, Object> role : roles) {
                if (user.id() == Integer.parseInt(String.valueOf(role.get("user_id")))) {
                    roleSet.add(Role.valueOf(String.valueOf(role.get("role"))));
                }
            }
            user.setRoles(roleSet);
        }
        return users;
    }

    private User getRolesToUser(User user) {
        if (Objects.isNull(user)) {
            return null;
        }
        user.setRoles(getRolesList(user));
        return user;
    }

    @Transactional
    public boolean deleteRole(User user, Role role) {
        return jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=? AND role=?", user.id(), role.name()) != 0;
    }

    @Transactional
    public boolean deleteAllRoles(User user) {
        return jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.id()) != 0;
    }

    @Transactional
    public User updateRoles(User user) {
        if (!user.getRoles().equals(new HashSet<>(getRolesList(user)))) {
            deleteAllRoles(user);
            setRoles(user);
        }
        return user;
    }

    @Transactional
    public User setRoles(User user) {
        List<Role> roles = new ArrayList<>(user.getRoles());
        if (!roles.isEmpty()) {
            jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES (?, ?)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement statement, int i) throws SQLException {
                            statement.setInt(1, user.id());
                            statement.setString(2, roles.get(i).name());
                        }

                        @Override
                        public int getBatchSize() {
                            return roles.size();
                        }
                    });
        }
        return user;
    }

    private List<Role> getRolesList(User user) {
        return jdbcTemplate.queryForList("SELECT role FROM user_roles WHERE user_id=?", Role.class, user.id());
    }
}
