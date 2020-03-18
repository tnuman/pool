package nl.tudelft.cse.sem.pool.gameobjects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TimeLimitTest {

    private transient TimeLimit timeLimit;

    @BeforeEach
    void setUp() {
        timeLimit = Mockito.spy(new TimeLimit());
    }

    @Test
    void notNull() {
        assertNotNull(timeLimit);
    }

    @Test
    void timerIsNotStopped() {
        Mockito.verify(timeLimit, Mockito.never()).notifyStop();
    }

    @Test
    void startTimer() {
        timeLimit.start();
        Mockito.verify(timeLimit, Mockito.never()).notifyStop();
    }

    @Test
    void stopTimerManual() {
        timeLimit.start();
        timeLimit.stop();
        Mockito.verify(timeLimit, Mockito.never()).notifyStop();
    }

    @Test
    void tickTimer() {
        timeLimit.start();
        timeLimit.tick();
        Mockito.verify(timeLimit, Mockito.never()).notifyStop();
    }

    @Test
    void tickTimerStop() {
        timeLimit.setTimer(Mockito.mock(Timer.class));
        timeLimit.setSeconds(0);
        timeLimit.tick();
        Mockito.verify(timeLimit).notifyStop();
    }

    @Test
    void timerStopListener() {
        TimeListener listener = Mockito.mock(TimeListener.class);
        timeLimit.addListener(listener);
        timeLimit.setTimer(Mockito.mock(Timer.class));
        timeLimit.setSeconds(0);
        timeLimit.tick();
        Mockito.verify(timeLimit).notifyStop();
        Mockito.verify(listener).stopped();

    }

    @Test
    void addNullListener() {
        assertFalse(timeLimit.addListener(null));
    }

    @Test
    void addListener() {
        assertTrue(timeLimit.addListener(new TimeListener()));
    }

    @Test
    void addSameListener() {
        TimeListener listener = new TimeListener();
        assertTrue(timeLimit.addListener(listener));
        assertFalse(timeLimit.addListener(listener));
    }
}
