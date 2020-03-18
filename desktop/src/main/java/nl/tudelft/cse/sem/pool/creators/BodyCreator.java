package nl.tudelft.cse.sem.pool.creators;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BodyCreator {

    private static final float FRICTION = 0.7f;
    private static final float DENSITY = 1700f;
    private static final float RESTITUTION = 1f;
    private transient World world;

    /**
     * Create a ball in the world.
     * @param radius radius of ball
     * @return body of the ball
     */
    public Body createBallBody(float radius) {
        //set physics
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 0);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = DENSITY;
        fixtureDef.restitution = RESTITUTION;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);

        body.setLinearDamping(FRICTION);
        body.setAngularDamping(FRICTION);
        body.setBullet(true);

        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();
        return body;
    }

}
