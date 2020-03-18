package nl.tudelft.cse.sem.pool.matchstate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.cse.sem.pool.gameobjects.CueBall;
import nl.tudelft.cse.sem.pool.gameobjects.NumberBall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BallSpeedCheckerTest {

    transient CueBall cueBall;
    transient NumberBall numberBall;
    transient List<NumberBall> numberBalls;
    transient BallSpeedChecker checker;

    @BeforeEach
    void setUp() {
        cueBall = Mockito.mock(CueBall.class, Mockito.RETURNS_DEEP_STUBS);
        numberBall = Mockito.mock(NumberBall.class);
        numberBalls = new ArrayList<>();
        numberBalls.add(numberBall);
        checker = Mockito.spy(new BallSpeedChecker(cueBall, numberBalls));
    }

    @Test
    void areBallsStopped() {
    }

    @Test
    void isCueBallStoppedFalse() {
        Mockito.doReturn(true).when(checker).isMoving(Mockito.any());
        assertFalse(checker.isCueBallStopped());
    }

    @Test
    void isCueBallStoppedTrue() {
        Mockito.doReturn(false).when(checker).isMoving(Mockito.any());
        assertTrue(checker.isCueBallStopped());
    }

    @Test
    void isNotMoving() {
        Mockito.when(cueBall.getBody().getLinearVelocity().len()).thenReturn(0f);
        assertFalse(checker.isMoving(cueBall));
    }

    @Test
    void isMoving() {
        Mockito.when(cueBall.getBody().getLinearVelocity().len()).thenReturn(10f);
        assertTrue(checker.isMoving(cueBall));
    }

    @Test
    void ballsNotStopped() {
        Mockito.doReturn(true).when(checker).isMoving(numberBall);
        assertFalse(checker.areBallsStopped());
    }

    @Test
    void ballsStopped() {
        Mockito.doReturn(false).when(checker).isMoving(numberBall);
        assertTrue(checker.areBallsStopped());
    }
}