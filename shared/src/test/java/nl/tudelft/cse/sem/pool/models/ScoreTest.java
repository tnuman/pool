package nl.tudelft.cse.sem.pool.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ScoreTest {

    @Test
    private void simpleConstructorTest() {
        var username = "username";
        var score = 42;

        var user = new User();
        user.setUsername(username);

        var scoreObj = new Score(user, score);

        assertNotNull(scoreObj);
        assertEquals(scoreObj.getUsername(), username);
        assertEquals(scoreObj.getUserScore(), score);
    }
}
