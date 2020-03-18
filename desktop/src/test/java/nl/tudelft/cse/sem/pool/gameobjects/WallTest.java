package nl.tudelft.cse.sem.pool.gameobjects;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WallTest {

    private transient World world;

    /**
     * setup for the tests.
     */
    @BeforeEach
    void setUp() {
        world = new World(new Vector2(0, 0), true);
    }

    /**
     * testing the constructor for the wall, tests if the wall exists.
     */
    @Test
    void constructorWallTest() {
        Wall wall = new Wall(world, 0, 0, 10, 10);
        assertNotNull(wall);
    }

}