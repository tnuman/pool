package nl.tudelft.cse.sem.pool.gameobjects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import nl.tudelft.cse.sem.pool.matchstate.BallObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

abstract class BallTest {

    transient Sprite sprite;
    transient World world;
    transient Ball ball;

    @BeforeEach
    abstract void setUp();

    @Test
    void constructorBallTest() {
        assertNotNull(ball);
        assertNotNull(ball.getBody());
    }

    @Test
    void placeTest() {
        assertFalse(ball.getBody().getFixtureList().get(0).isSensor());
        ball.release();
        assertTrue(ball.getBody().getFixtureList().get(0).isSensor());
    }

    @Test
    void releaseTest() {
        ball.release();
        ball.place();
        assertFalse(ball.getBody().getFixtureList().get(0).isSensor());
    }

    @Test
    void setPositionTest() {
        ball.setPosition(10, 20);
        assertEquals(ball.getX(), 10);
        assertEquals(ball.getY(), 20);
        assertEquals(ball.getBody().getPosition(), new Vector2(10, 20));
    }

    @Test
    void updatePositionTest() {
        ball.getBody().setTransform(1, 2, 0);
        ball.updatePosition();
        assertEquals(1, ball.getX());
        assertEquals(2, ball.getY());
    }
    
    @Test
    void addObserverTest() {
        BallObserver ballObserver = Mockito.mock(BallObserver.class);
        ball.addObserver(ballObserver);
        assertTrue(ball.getObservers().contains(ballObserver));
    }

    @Test
    void removeObserverTest() {
        BallObserver ballObserver = Mockito.mock(BallObserver.class);
        ball.addObserver(ballObserver);
        ball.removeObserver(ballObserver);
        assertFalse(ball.getObservers().contains(ballObserver));
    }
}