package nl.tudelft.cse.sem.pool.security;

import static nl.tudelft.cse.sem.pool.security.SecurityConstants.HEADER_STRING;
import static nl.tudelft.cse.sem.pool.security.SecurityConstants.TOKEN_PREFIX;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class JWTAuthorizationFilterTest {

    @Mock
    private transient HttpServletRequest req;

    @Mock
    private transient HttpServletResponse res;

    @Mock
    private transient FilterChain chain;

    @Mock
    private transient AuthenticationManager authManager;

    @Test
    void doFilterInternalPartiallyValidToken() {
        JWTAuthorizationFilter filter = new JWTAuthorizationFilter(authManager);

        // Token with valid start but invalid ending.
        when(req.getHeader(HEADER_STRING)).thenReturn(TOKEN_PREFIX);

        assertThrows(ServletException.class, () -> {
            filter.doFilterInternal(req, res, chain);
        });
    }

    @Test
    void doFilterInternalInvalidToken() throws IOException, ServletException {
        JWTAuthorizationFilter filter = new JWTAuthorizationFilter(authManager);

        // Token with valid start but invalid ending.
        when(req.getHeader(HEADER_STRING)).thenReturn("Not A Token Prefix");

        filter.doFilterInternal(req, res, chain);

        verify(chain, times(1)).doFilter(req, res);
    }
}
