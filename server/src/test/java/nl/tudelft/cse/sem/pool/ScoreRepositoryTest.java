package nl.tudelft.cse.sem.pool;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;
import nl.tudelft.cse.sem.pool.database.repositories.ScoreRepository;
import nl.tudelft.cse.sem.pool.models.Score;
import nl.tudelft.cse.sem.pool.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ScoreRepositoryTest {

    @Autowired
    private transient ScoreRepository scoreRepository;

    private transient User user;
    private transient Score score1;
    private transient String username = "test";

    /** Setup for the following tests.
     *
     */
    @BeforeEach
    public void setUp() {
        scoreRepository.setUp(true);
        user = new User(username, "pwd");
        score1 = new Score(user, 920);
        scoreRepository.save(score1);
    }

    @Test
    public void userSetUpNotForced() {
        scoreRepository.setUp(false);
        assertThatCode(() -> scoreRepository.findByUsername(username)).doesNotThrowAnyException();
    }

    @Test
    public void testCount() {
        assertEquals(1, scoreRepository.count());

        User user2 = new User("test2", "ptest2");
        Score score2 = new Score(user2, 450);
        scoreRepository.save(score2);
        assertEquals(2, scoreRepository.count());

        User user3 = new User("test3", "ptest3");
        Score score3 = new Score(user3, 350);
        scoreRepository.save(score3);
        assertEquals(3, scoreRepository.count());
    }

    @Test
    public void testGetScoreByUsername() {
        Score getScore = scoreRepository.findByUsername(username).get();
        assertEquals(score1.getUsername(), getScore.getUsername());
        assertEquals(score1.getUserScore(), getScore.getUserScore());
    }

    @Test
    public void testSaveScore() {
        User newUser = new User("le_me", "trololz");
        Score newScore = new Score(newUser, 250);
        assertNotEquals(0, scoreRepository.save(newScore));
        Score getScore = scoreRepository.findByUsername("le_me").get();
        assertEquals(newScore.getUsername(), getScore.getUsername());
        assertEquals(newScore.getUserScore(), getScore.getUserScore());
    }

    @Test
    public void testUpdateScore() {
        Score newScore = new Score(user, 650);
        assertNotEquals(0, scoreRepository.update(newScore));

        assertEquals(newScore.getUserScore(),
                scoreRepository.findByUsername(username).get().getUserScore());
    }

    @Test
    public void testGetTop5() {
        User user2 = new User("test2", "ptest2");
        Score score2 = new Score(user2, 1);
        scoreRepository.save(score2);

        User user3 = new User("test3", "ptest3");
        Score score3 = new Score(user3, 2);
        scoreRepository.save(score3);

        User user4 = new User("test4", "ptest4");
        Score score4 = new Score(user4, 3);
        scoreRepository.save(score4);

        User user5 = new User("test5", "ptest5");
        Score score5 = new Score(user5, 4);
        scoreRepository.save(score5);

        User user6 = new User("test6", "test6");
        Score score6 = new Score(user6, 5);
        scoreRepository.save(score6);

        List<Score> top5 = scoreRepository.getTop5();

        assertEquals(top5.get(4).getUsername(), score6.getUsername());
        assertEquals(top5.get(4).getUserScore(), score6.getUserScore());

        assertEquals(top5.get(3).getUsername(), score5.getUsername());
        assertEquals(top5.get(3).getUserScore(), score5.getUserScore());

        assertEquals(top5.get(2).getUsername(), score4.getUsername());
        assertEquals(top5.get(2).getUserScore(), score4.getUserScore());

        assertEquals(top5.get(1).getUsername(), score3.getUsername());
        assertEquals(top5.get(1).getUserScore(), score3.getUserScore());

        assertEquals(top5.get(0).getUsername(), score2.getUsername());
        assertEquals(top5.get(0).getUserScore(), score2.getUserScore());
    }
}
