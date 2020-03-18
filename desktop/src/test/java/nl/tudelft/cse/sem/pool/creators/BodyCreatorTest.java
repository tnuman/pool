package nl.tudelft.cse.sem.pool.creators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.jupiter.api.Test;

class BodyCreatorTest {

    @Test
    void createBallBody() {
        World world = new World(new Vector2(0, 0), true);
        BodyCreator bodyCreator = new BodyCreator(world);
        Body body = bodyCreator.createBallBody(6f);
        assertNotNull(body);
        assertEquals(0.7f, body.getLinearDamping());
        assertEquals(new Vector2(0, 0), body.getPosition());
    }
}