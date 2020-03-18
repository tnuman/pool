package nl.tudelft.cse.sem.pool.gameobjects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import nl.tudelft.cse.sem.pool.creators.BodyCreator;
import nl.tudelft.cse.sem.pool.matchstate.BallObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CueBallTest extends BallTest {

    private transient CueBall cueBall;
    
    @BeforeEach
    void setUp() {
        world = new World(new Vector2(0,0), true);
        sprite = new Sprite();
        ball = new CueBall(new BodyCreator(world), 1f, sprite);
        cueBall = (CueBall) ball;
    }

    @Test
    void cueBallConstructorTest() {
        assertNotNull(ball);
    }

    @Test
    void applyImpulseTest() {
        cueBall.applyImpulse(new Vector2(1, 0), 1000);
        System.out.println(cueBall.getBody().getLinearVelocity());
        // 187... speed is after impulse
        assertEquals(new Vector2(0.1872411f, 0), cueBall.getBody().getLinearVelocity());
    }

    @Test
    void pocketTest() {
        Pocket pocket = Mockito.mock(Pocket.class);
        BallObserver ballObserver = Mockito.mock(BallObserver.class);
        cueBall.addObserver(ballObserver);
        cueBall.pocket(pocket);
        Mockito.verify(ballObserver).potCueBall(cueBall);
    }
}