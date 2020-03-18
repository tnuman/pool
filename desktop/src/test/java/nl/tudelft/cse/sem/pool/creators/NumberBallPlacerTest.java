package nl.tudelft.cse.sem.pool.creators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.util.List;
import nl.tudelft.cse.sem.pool.gameobjects.NumberBall;
import nl.tudelft.cse.sem.pool.gui.Sprites;
import nl.tudelft.cse.sem.pool.matchstate.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class NumberBallPlacerTest extends BallPlacerTest {
    
    private transient NumberBallPlacer numberBallFactory;
    
    @BeforeEach
    void setUp() {
        sprites = Mockito.spy(new Sprites());
        Mockito.doReturn(null).when(sprites).loadSprite(Mockito.anyString());
        world = new World(new Vector2(0, 0), true);
        bodyCreator = new BodyCreator(world);
        ballPlacer = new NumberBallPlacer(sprites, bodyCreator, 1);
        numberBallFactory = (NumberBallPlacer) ballPlacer;
        match = Mockito.mock(Match.class);
        stage = Mockito.mock(Stage.class);
    }

    @Test
    void placeNumberBalls() {
        List<NumberBall> result = numberBallFactory.placeNumberBalls(
                new Vector2(1,2), Mockito.mock(Match.class), Mockito.mock(Stage.class));
        assertEquals(15, result.size());
        assertEquals(2, result.get(0).getY());
    }
    
    @Test 
    @Override // Test for abstract factory method
    void testCreateBall() {
        NumberBall ball = numberBallFactory.createBall();
        assertEquals(numberBallFactory.getCurrentNumber(), ball.getNumber());
        assertEquals(numberBallFactory.RADIUS, ball.getRadius());
    }
}