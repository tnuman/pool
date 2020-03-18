package nl.tudelft.cse.sem.pool.gameobjects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Pocket extends Image {

    private transient Body body;
    private transient float radius;

    /**
     * Initializes a new pocket.
     *
     * @param world   world for the elements to reside in
     * @param x       x coordinate for the pocket
     * @param y       y coordinate for the pocket
     * @param radius1 radius for the pocket
     */
    public Pocket(World world, float x, float y, float radius1) {
        // set graphics
        this.setPosition(x, y);
        radius = radius1;
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        //set physics
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);

        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();
        body.setUserData(this);
        this.setOrigin(this.getWidth() / 2, this.getHeight() / 2);
    }
}
