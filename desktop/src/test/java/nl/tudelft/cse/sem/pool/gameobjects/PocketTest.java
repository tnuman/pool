package nl.tudelft.cse.sem.pool.gameobjects;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PocketTest {

    private transient World world;

    /**
     * setup for the tests.
     */
    @BeforeEach
    void setUp() {
        world = new World(new Vector2(0,0), true);
    }

    /**
     * test for the constructor of pocket, checks if pocket exists.
     */
    @Test
    void constructorPocketTest() {
        Pocket pocket = new Pocket(world, 0, 0, 1);
        assertNotNull(pocket);
    }

}