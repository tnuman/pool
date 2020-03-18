package nl.tudelft.cse.sem.pool.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import nl.tudelft.cse.sem.pool.models.Score;
import nl.tudelft.cse.sem.pool.models.User;
import org.junit.jupiter.api.Test;


class ApiWrapperTest {

    @Test
    void loginUsernameEmpty() {
        var wrapper = new ApiWrapper();

        var user = new User();
        user.setPassword("abcd");

        assertFalse(wrapper.login(user));
    }

    @Test
    void loginPasswordEmpty() {
        var wrapper = new ApiWrapper();

        var user = new User();
        user.setUsername("efgh");

        assertFalse(wrapper.login(user));
    }

    @Test
    void signupUsernameEmpty() {
        var wrapper = new ApiWrapper();

        var user = new User();
        user.setPassword("jklm");

        assertFalse(wrapper.signUp(user));
    }

    @Test
    void signupPasswordEmpty() {
        var wrapper = new ApiWrapper();

        var user = new User();
        user.setUsername("nopqr");

        assertFalse(wrapper.signUp(user));
    }

    @Test
    void validJWT() {
        var wrapper = new ApiWrapper();
        var token = "Bearer SomeTokenStringHere";

        wrapper.setJWTTokenHeader(token);

        assertEquals(Unirest.config().getDefaultHeaders().getFirst("Authorization"), token);
    }

    @Test
    void invalidJWT() {
        var wrapper = new ApiWrapper();
        var token = "SomeTokenStringHere";

        assertThrows(AssertionError.class, ()  -> {
            wrapper.setJWTTokenHeader(token);
        });
    }

    @Test
    void nullJWT() {
        var wrapper = new ApiWrapper();
        String token = "";

        assertThrows(AssertionError.class, () -> {
            wrapper.setJWTTokenHeader(token);
        });
    }

    @Test
    void testInvalidLogin() {
        var wrapper = new ApiWrapper();
        var user = new User("non null user", "non null pass");

        // An invalid server
        wrapper.setBaseUrl("localhost:0");

        assertThrows(UnirestException.class, () -> {
            wrapper.login(user);
        });

    }

    @Test
    void testNullUserSignUp() {
        var wrapper = new ApiWrapper();
        User user = null;

        assertThrows(NullPointerException.class, () -> {
            wrapper.signUp(user);
        });
    }

    @Test
    void testNullUserLogin() {
        var wrapper = new ApiWrapper();
        User user = null;

        assertThrows(NullPointerException.class, () -> {
            wrapper.login(user);
        });
    }

    @Test
    void testSubmitScoreNull() {
        var wrapper = new ApiWrapper();

        assertThrows(NullPointerException.class, () -> {
            wrapper.submitScore(null);
        });
    }



}
