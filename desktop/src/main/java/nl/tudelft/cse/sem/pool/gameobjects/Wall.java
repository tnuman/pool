package nl.tudelft.cse.sem.pool.gameobjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Wall extends Image {

    private transient Body body;
    private transient float width;
    private transient float height;

    /**
     * Constructor for Wall.
     * @param world1 world for the elements to reside in
     * @param x x position for the origin of the wall
     * @param y y position for the origin of the wall
     * @param width1 width for the wall
     * @param height1 height for the wall
     */
    public Wall(World world1, float x, float y, float width1, float height1) {
        // set graphics
        this.setPosition(x, y);

        width = width1;
        height = height1;

        //set physics
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x + width / 2, y + height / 2);

        body = world1.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef);
        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();
        body.setUserData(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
