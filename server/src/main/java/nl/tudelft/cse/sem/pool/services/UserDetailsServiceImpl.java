package nl.tudelft.cse.sem.pool.services;

import static java.util.Collections.emptyList;

import java.util.Optional;

import lombok.AllArgsConstructor;
import nl.tudelft.cse.sem.pool.database.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<nl.tudelft.cse.sem.pool.models.User> maybeApplicationUser;
        maybeApplicationUser = applicationUserRepository.findByUsername(username);

        if (maybeApplicationUser.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        nl.tudelft.cse.sem.pool.models.User applicationUser = maybeApplicationUser.get();

        return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
    }
}
