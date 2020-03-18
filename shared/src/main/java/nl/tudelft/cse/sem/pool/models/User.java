package nl.tudelft.cse.sem.pool.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class that represents a User row from the database.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    private Long id;
    private String username;
    private String password;

    /** Constructor for the client.
     *
     * @param username  name of the user
     * @param password  user's password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
