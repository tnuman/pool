package nl.tudelft.cse.sem.pool.database.repositories;

import java.util.Optional;

import nl.tudelft.cse.sem.pool.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserRepository implements UserRepository {

    // Spring Boot will create and configure DataSource and JdbcTemplate
    // To use it, just @Autowired
    @Autowired
    private transient JdbcTemplate jdbcTemplate;

    private transient boolean initialized = false;

    @Override
    public void setUp(boolean force) {
        if (!initialized || force) {
            jdbcTemplate.execute("DROP TABLE IF EXISTS users");
            jdbcTemplate.execute("CREATE TABLE users("
                    + "id SERIAL, username VARCHAR(255) UNIQUE NOT NULL, password VARCHAR(255))");
            initialized = true;
        }
    }

    @Override
    public int count() {
        return jdbcTemplate
                .queryForObject("select count(*) from users", Integer.class);
    }

    @Override
    public int save(User user) {
        return jdbcTemplate.update(
                "insert into users (username, password) values(?,?)",
                user.getUsername(), user.getPassword());
    }

    @Override
    public int update(User user) {
        return jdbcTemplate.update(
                "update users set password = ? where username = ?",
                user.getPassword(), user.getUsername());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return  jdbcTemplate.queryForObject(
            "select * from users where username = ?",
            new Object[]{username},
            (rs, rowNum) ->
                Optional.of(new User(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("password")
                ))
        );
    }
}
