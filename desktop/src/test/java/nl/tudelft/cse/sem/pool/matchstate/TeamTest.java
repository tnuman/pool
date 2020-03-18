package nl.tudelft.cse.sem.pool.matchstate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TeamTest {

    @Test
    void testReverseSolid() {
        assertEquals(Team.SOLID.reverse(), Team.STRIPES);
    }

    @Test
    void testReverseStripes() {
        assertEquals(Team.STRIPES.reverse(), Team.SOLID);
    }

    @Test
    void testReverseUndetermined() {
        assertEquals(Team.UNDETERMINED.reverse(), Team.UNDETERMINED);
    }
}
