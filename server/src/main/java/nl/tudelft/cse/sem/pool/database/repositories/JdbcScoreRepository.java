package nl.tudelft.cse.sem.pool.database.repositories;

import java.util.List;
import java.util.Optional;
import nl.tudelft.cse.sem.pool.models.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcScoreRepository implements ScoreRepository {

    // Spring Boot will create and configure DataSource and JdbcTemplate
    // To use it, just @Autowired
    @Autowired
    private transient JdbcTemplate jdbcTemplate;

    private transient boolean initialized = false;

    @Override
    public void setUp(boolean force) {
        if (!initialized || force) {
            jdbcTemplate.execute("DROP TABLE IF EXISTS scores");
            jdbcTemplate.execute("CREATE TABLE scores("
                    + "id SERIAL, username VARCHAR(255) UNIQUE NOT NULL,"
                    + "score INTEGER)");
            initialized = true;
        }

    }

    public int count() {
        return jdbcTemplate
                .queryForObject("select count(*) from scores", Integer.class);
    }

    @Override
    public int save(Score score) {
        return jdbcTemplate.update(
                "insert into scores (username, score) values(?,?)",
                score.getUsername(), score.getUserScore());
    }

    @Override
    public int update(Score score) {
        return jdbcTemplate.update(
                "update scores set score = ? where username = ?",
                score.getUserScore(), score.getUsername());
    }

    @Override
    public List<Score> getTop5() {
        return jdbcTemplate.query(
            "select * from scores order by score asc limit 5",
            (rs, rowNum) ->
                new Score(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getInt("score"))
        );
    }

    // The suppress is needed to able to define result in either the try or catch branch.
    @Override
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public Optional<Score> findByUsername(String username) {
        // https://github.com/spring-projects/spring-framework/issues/17262

        Optional<Score> result;

        try {
            result = jdbcTemplate.queryForObject(
                    "select * from scores where username = ?",
                    new Object[]{username},
                (rs, rowNum) ->
                        Optional.of(new Score(
                                rs.getLong("id"),
                                rs.getString("username"),
                                rs.getInt("score")))
            );
        } catch (EmptyResultDataAccessException e) {
            // No rows matching username
            result =  Optional.empty();
        }

        return result;
    }
}
