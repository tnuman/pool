package nl.tudelft.cse.sem.pool.database.repositories;

import java.util.List;
import java.util.Optional;
import nl.tudelft.cse.sem.pool.models.Score;

/** Interface for the Score repository.
 *
 */
public interface ScoreRepository {

    void setUp(boolean force);

    /** Get the number of stored scores.
     *
     * @return      the number of stored scores
     */
    int count();

    /** Store the user's score to the database.
     *
     * @param score the score the user wants to store
     * @return      succeeded or failed to store the score
     */
    int save(Score score);

    /** Update the user's score.
     *
     * @param score the score the user wants to update
     * @return      succeeded or failed to store the score
     */
    int update(Score score);

    /** Get the top 5 scores from the database.
     *
     * @return a list with the top 5 scores
     */
    List<Score> getTop5();

    /** Find a score by the username.
     *
     * @param username  the username whose score we want to fin
     * @return          the user's Score object
     */
    Optional<Score> findByUsername(String username);

}
