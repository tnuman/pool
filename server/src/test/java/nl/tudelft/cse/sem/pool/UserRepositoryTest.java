package nl.tudelft.cse.sem.pool;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import nl.tudelft.cse.sem.pool.database.repositories.UserRepository;
import nl.tudelft.cse.sem.pool.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private transient UserRepository userRepository;

    private transient User user;
    private transient String username = "test";

    /** Setup for the following tests.
     *
     */
    @BeforeEach
    public void setUp() {
        userRepository.setUp(true);
        user = new User(username, "pwd");
        userRepository.save(user);
    }

    @Test
    public void userSetUpNotForced() {
        userRepository.setUp(false);
        assertThatCode(() -> userRepository.findByUsername("test")).doesNotThrowAnyException();
    }

    @Test
    public void testCount() {
        assertEquals(1, userRepository.count());
        User user2 = new User("test2", "test2");
        userRepository.save(user2);
        assertEquals(2, userRepository.count());

        User user3 = new User("test3", "test3");
        userRepository.save(user3);
        assertEquals(3, userRepository.count());

        User user4 = new User("test4", "test4");
        userRepository.save(user4);
        assertEquals(4, userRepository.count());

        User user5 = new User("test5", "test5");
        userRepository.save(user5);
        assertEquals(5, userRepository.count());
    }

    @Test
    public void testGetUserByUsername() {
        User getUser = userRepository.findByUsername(username).get();
        assertEquals(user.getUsername(), getUser.getUsername());
        assertEquals(user.getPassword(), getUser.getPassword());
    }

    @Test
    public void testSaveUser() {
        User newUser = new User("le_me", "trololz");
        assertNotEquals(0, userRepository.save(newUser));
        User getUser = userRepository.findByUsername("le_me").get();
        assertEquals(newUser.getUsername(), getUser.getUsername());
        assertEquals(newUser.getPassword(), getUser.getPassword());
    }

    @Test
    public void testUpdateUser() {
        User newUser = new User(username, "newpwd");
        assertNotEquals(0, userRepository.update(newUser));
        assertEquals(newUser.getPassword(),
                userRepository.findByUsername("test").get().getPassword());
    }

}
