package nl.tudelft.cse.sem.pool.creators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import com.badlogic.gdx.scenes.scene2d.Stage;
import nl.tudelft.cse.sem.pool.gameobjects.CueBall;
import nl.tudelft.cse.sem.pool.gui.Sprites;
import nl.tudelft.cse.sem.pool.matchstate.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CueBallPlacerTest extends BallPlacerTest {
    
    private transient CueBallPlacer cueBallFactory;

    @BeforeEach
    void setUp() {
        sprites = Mockito.spy(new Sprites());
        Mockito.doReturn(null).when(sprites).loadSprite(Mockito.anyString());
        world = new World(new Vector2(0, 0), true);
        bodyCreator = new BodyCreator(world);
        ballPlacer = new CueBallPlacer(sprites, bodyCreator);
        cueBallFactory = (CueBallPlacer) ballPlacer;
        match = Mockito.mock(Match.class);
        stage = Mockito.mock(Stage.class);
    }

    @Test 
    @Override // Test for abstract factory method
    void testCreateBall() {
        CueBall ball = ((CueBallPlacer) ballPlacer).createBall();
        assertEquals(ballPlacer.RADIUS, ball.getRadius());
    }
}