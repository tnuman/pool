package nl.tudelft.cse.sem.pool.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    private void simpleConstructorTest() {
        var username = "username";
        var password = "password";

        var user = new User(username, password);

        assertNotNull(user);
        assertEquals(user.getUsername(), username);
        assertEquals(user.getPassword(), password);
    }
}
