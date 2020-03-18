package nl.tudelft.cse.sem.pool.gameobjects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.math.Vector2;

import nl.tudelft.cse.sem.pool.gui.Sprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CueTest {
    
    private transient Cue cue;
    private transient CueBall ball;
    private transient Sprites sprites;
     
    @BeforeEach
    void setUp() {
        sprites = Mockito.spy(new Sprites());
        Mockito.doReturn(null).when(sprites).loadSprite(Mockito.anyString());
        ball = Mockito.mock(CueBall.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(ball.getBody().getPosition()).thenReturn(new Vector2(4, 5));
        cue = new Cue(ball, sprites);
    }
    
    @Test
    void constructorCueTest() {
        assertNotNull(cue);
        assertEquals(new Vector2(4, 5), cue.getBallPos());
    }
    
    @Test
    void changeDragTest() {
        cue.changeDrag(0.5f);
        assertEquals(0.5f, cue.getDrag());
    }
    
    @Test
    void changeDragSmallDecreaseTest() {
        cue.changeDrag(0.5f);
        cue.changeDrag(-0.1f);
        assertEquals(0.4f, cue.getDrag());
    }
    
    @Test
    void changeDragMinTest() {
        cue.changeDrag(0.5f);
        cue.changeDrag(-0.4f);
        assertEquals(0, cue.getDrag());
    }
    
    @Test
    void changeDragMaxTest() {
        cue.changeDrag(5f);
        assertEquals(3.f, cue.getDrag());
    }
    
    @Test
    void shootTest() {
        cue.changeDrag(0.5f);
        assertTrue(cue.shoot(new Vector2(2, 3)));
        assertFalse(cue.isVisible());
    }
    
    @Test
    void noShotTest() {
        cue.shoot(new Vector2(2, 3));
        assertFalse(cue.shoot(new Vector2(2, 3)));
        assertTrue(cue.isVisible());
    }

    @Test
    void normPowerBetween() {
        cue.changeDrag(1.29f);
        assertEquals(0.4, cue.getNormPower(), 0.001);
    }

    @Test
    void normPowerZero() {
        assertEquals(0, cue.getNormPower());
    }
}
