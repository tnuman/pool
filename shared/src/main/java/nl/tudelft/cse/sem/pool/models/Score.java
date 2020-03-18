package nl.tudelft.cse.sem.pool.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class that represents a Score row from the database.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Score {

    private Long id;
    private String username;
    private int userScore;

    /**
     * Constructor for the client.
     *
     * @param user      the user who made the score
     * @param score     the score the user made
     */
    public Score(User user, int score) {
        this.username = user.getUsername();
        this.userScore = score;
    }
}
