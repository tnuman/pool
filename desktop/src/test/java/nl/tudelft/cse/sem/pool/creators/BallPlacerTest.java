package nl.tudelft.cse.sem.pool.creators;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import nl.tudelft.cse.sem.pool.gameobjects.Ball;
import nl.tudelft.cse.sem.pool.gui.Sprites;
import nl.tudelft.cse.sem.pool.matchstate.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

abstract class BallPlacerTest {
    //Package-private since they need to be used in sub class test classes
    transient Sprites sprites;
    transient World world;
    transient BodyCreator bodyCreator;
    transient BallPlacer ballPlacer;
    transient Match match;
    transient Stage stage;

    // Abstract setup method where class attributes are assigned
    // Should be overridden by subclasses
    @BeforeEach
    abstract void setUp();
    
    @Test
    void ballFactoryConstructor() {
        //Should be constructed by subclass in setup method
        assertNotNull(ballPlacer);
    }
    
    @Test
    void testPlaceBall() {
        ballPlacer.placeBall(new Vector2(2,1), match, stage);
        Mockito.verify(stage, Mockito.times(1)).addActor(Mockito.any(Ball.class));
    }
    
    // Test for abstract factory method
    @Test
    abstract void testCreateBall();
}