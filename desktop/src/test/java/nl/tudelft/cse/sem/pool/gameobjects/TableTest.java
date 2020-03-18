package nl.tudelft.cse.sem.pool.gameobjects;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import nl.tudelft.cse.sem.pool.gui.Sprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TableTest {

    private transient World world;
    private transient Table table;
    private transient Sprites sprites;
    private transient Stage stage;

    /**
     * Setup for the tests.
     */
    @BeforeEach
    void setUp() {
        world = new World(new Vector2(0, 0), true);
        sprites = Mockito.spy(new Sprites());
        Mockito.doReturn(null).when(sprites).loadSprite(Mockito.anyString());
        stage = Mockito.mock(Stage.class);
        table = new Table(world, 10, 10, 1, 10, 100,
                100, sprites, stage);
    }

    /**
     * Test the constructor for the table.
     * Tests whether the table exists and all elements are added to the stage
     */
    @Test
    void constructorTableTest() {
        assertNotNull(table);
        // Verify 6 pockets, 6 walls and the table itself have been added to the stage
        Mockito.verify(stage, Mockito.times(6)).addActor(Mockito.any(Wall.class));
        Mockito.verify(stage, Mockito.times(6)).addActor(Mockito.any(Pocket.class));
        Mockito.verify(stage, Mockito.times(1)).addActor(Mockito.any(Table.class));
    }
}