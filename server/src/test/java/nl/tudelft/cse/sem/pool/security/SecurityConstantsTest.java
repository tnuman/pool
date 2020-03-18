package nl.tudelft.cse.sem.pool.security;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class SecurityConstantsTest {

    @Test
    public void testConstants() {
        SecurityConstants constants = new SecurityConstants();
        Assert.assertNotNull(constants);
    }
}
