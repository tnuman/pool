package nl.tudelft.cse.sem.pool.matchstate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StateTest {

    @Test
    void testReversePlayer1() {
        assertEquals(State.PLAYER2_TURN, State.PLAYER1_TURN.reverse());
    }

    @Test
    void testReversePlayer2() {
        assertEquals(State.PLAYER1_TURN, State.PLAYER2_TURN.reverse());
    }

    @Test
    void testReverseNotTurn() {
        assertEquals(State.SETUP, State.SETUP.reverse());
    }
}
