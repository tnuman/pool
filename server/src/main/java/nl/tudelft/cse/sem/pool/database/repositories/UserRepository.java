package nl.tudelft.cse.sem.pool.database.repositories;

import java.util.Optional;

import nl.tudelft.cse.sem.pool.models.User;

/** Interface for the User repository.
 *
 */
public interface UserRepository {

    void setUp(boolean force);

    /** Get the number of stored scores.
     *
     * @return          the number of stored scores
     */
    int count();

    /** Store the user to the database.
     *
     * @param user      the user we want to store
     * @return          succeeded or failed to store the user
     */
    int save(User user);

    /** Update the user's password.
     *
     * @param user      the user with the new password
     * @return          succeeded or failed to store the updated user
     */
    int update(User user);

    /** Find a user by its username.
     *
     * @param username  the username of the user
     * @return          the User with the requested username
     */
    Optional<User> findByUsername(String username);
}
