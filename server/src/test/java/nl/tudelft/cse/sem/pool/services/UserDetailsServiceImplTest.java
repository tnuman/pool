package nl.tudelft.cse.sem.pool.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import nl.tudelft.cse.sem.pool.database.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserDetailsServiceImplTest {

    @Mock
    private transient UserRepository userRepository;

    @Test
    void testLoadUserByUsernameFail() {
        String username = "NonExistingUser";

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            new UserDetailsServiceImpl(userRepository).loadUserByUsername(username);
        });
    }
}
