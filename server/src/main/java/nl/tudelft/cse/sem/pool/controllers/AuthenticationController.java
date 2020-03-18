package nl.tudelft.cse.sem.pool.controllers;

import lombok.AllArgsConstructor;
import nl.tudelft.cse.sem.pool.database.repositories.UserRepository;
import nl.tudelft.cse.sem.pool.models.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final UserRepository repository;

    private static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Creates and returns a user with the given username and password.
     *
     * @return The created user.
     */
    @PostMapping(value = "/signup")
    public User createUser(@RequestBody User user)  {
        repository.setUp(false);

        user.setPassword(hashPassword(user.getPassword())); // Hash the password

        repository.save(user);

        user.setPassword(""); // Don't leak the (even the hashed) password
        return user;
    }
}
