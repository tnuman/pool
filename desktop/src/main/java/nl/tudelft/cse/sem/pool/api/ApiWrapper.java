package nl.tudelft.cse.sem.pool.api;

import java.util.List;

import kong.unirest.GenericType;
import kong.unirest.Unirest;
import lombok.NonNull;
import lombok.Setter;
import nl.tudelft.cse.sem.pool.models.Score;
import nl.tudelft.cse.sem.pool.models.User;


public class ApiWrapper {

    @Setter
    private transient String baseUrl = "http://localhost:8080";
    private static final String AUTHORIZATION = "Authorization";

    private static void configure() {
        Unirest.config()
                .setDefaultHeader("Accept", "application/json")
                .setDefaultHeader("Content-Type", "application/json");
    }

    public void setJWTTokenHeader(String token) {
        assert token.startsWith("Bearer");
        Unirest.config().setDefaultHeader(AUTHORIZATION, token);
    }

    /**
     * Logs the user in and sets the token header in the Unirest singleton
     * so that future requests are authenticated.
     *
     * @param user The user to login with.
     * @return a boolean signifying if the login went successful.
     */
    public boolean login(@NonNull User user) {
        configure();

        if (user.getUsername() == null || user.getPassword() == null) {
            return false;
        }

        var response = Unirest.post(baseUrl + "/login").body(user).asJson();

        System.out.println("Login status:" + response.getStatus() + response.getStatusText());

        String token = response.getHeaders().getFirst(AUTHORIZATION);

        setJWTTokenHeader(token);

        return response.isSuccess();
    }

    /**
     * Creates an account for the given User.
     *
     * @param user The user to create
     * @return a boolean signifying if the signup went successful.
     */
    public boolean signUp(@NonNull User user) {
        configure();

        if (user.getUsername() == null || user.getPassword() == null) {
            return false;
        }

        var response = Unirest.post(baseUrl + "/signup").body(user).asJson();

        System.out.println("Sign Up status: " + response.getStatus() + response.getStatusText());

        return response.isSuccess();
    }

    /**
     * Submits the score at the end of a game to the server.
     * @param score the score to be submitted.
     * @return a boolean which is true on success and false on failure.
     */
    public boolean submitScore(@NonNull Score score) {
        configure();

        var response = Unirest.post(baseUrl + "/score").body(score).asJson();

        System.out.println("Submit Score response:"
                + response.getStatus() + response.getStatusText());

        return response.isSuccess();
    }

    /**
     * Gets the top 5 on the current leaderboard.
     * @return a list containg the top five scores.
     */
    public List<Score> getTop5() {
        configure();

        List<Score> scores;

        var response = Unirest.get(baseUrl + "/topscores")
                .asObject(new GenericType<List<Score>>(){});

        scores = response.getBody();

        return scores;
    }

    /**
     * Logs the user out.
     */
    public static void logout() {
        Unirest.config().reset();
    }
}
