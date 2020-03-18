package nl.tudelft.cse.sem.pool.gameobjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nl.tudelft.cse.sem.pool.creators.BodyCreator;
import nl.tudelft.cse.sem.pool.matchstate.BallObserver;

public abstract class Ball extends Image {

    private @Getter transient Body body;
    private @Getter transient float radius;
    private transient Sprite sprite;
    @Getter List<BallObserver> observers;

    /**
     * Constructs the ball and sets up the properties for Box2d.
     * @param bodyCreator factory creating the body for physics
     * @param radius radius of ball
     * @param sprite sprite ball has
     */
    public Ball(BodyCreator bodyCreator, float radius, Sprite sprite) {
        super(sprite);
        this.radius = radius;
        this.sprite = sprite;
        this.body = bodyCreator.createBallBody(radius);
        body.setUserData(this);
        this.setOrigin(this.getWidth() / 2,this.getHeight() / 2);
        this.observers = new ArrayList<>();
    }

    public void place() {
        body.getFixtureList().get(0).setSensor(false);
    }

    public void release() {
        resetVelocity();
        body.getFixtureList().get(0).setSensor(true);
    }

    /**
     * Set the position of the ball in the object itself and its body.
     * @param x new x position
     * @param y new y position
     */
    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        body.setTransform(x, y, 0);
    }

    /**
     * Updates the position of the visible ball, based on the position of the body.
     */
    public void updatePosition() {
        Vector2 newPos = body.getPosition();
        super.setPosition(newPos.x, newPos.y);
    }

    /**
     * Add an observer for this ball.
     * @param observer the observer to add for this ball
     */
    public void addObserver(BallObserver observer) {
        observers.add(observer);
    }

    /**
     * Remove an observer for this ball.
     * @param observer the observer to remove for this ball
     */
    public void removeObserver(BallObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notify the observers the ball is pocketed.
     * @param pocket the pocket
     */
    public abstract void pocket(Pocket pocket);

    /**
     * Resets the velocity.
     */
    public void resetVelocity() {
        body.setLinearVelocity(0, 0);
        body.setAngularVelocity(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sprite, getX() - radius, getY() - radius, radius, radius,
            2 * radius, 2 * radius, 1f, 1f, (float) Math.toDegrees(body.getAngle()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        updatePosition();
    }
}
