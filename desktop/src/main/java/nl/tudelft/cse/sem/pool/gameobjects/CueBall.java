package nl.tudelft.cse.sem.pool.gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import nl.tudelft.cse.sem.pool.creators.BodyCreator;
import nl.tudelft.cse.sem.pool.matchstate.BallObserver;

public class CueBall extends Ball {
    
    /**
     * Constructs the ball and sets up the properties for Box2d.
     *
     * @param bodyCreator factory creating the body for physics
     * @param radius      radius of ball
     * @param sprite      sprite ball has
     */
    public CueBall(BodyCreator bodyCreator, float radius, Sprite sprite) {
        super(bodyCreator, radius, sprite);
    }

    /**
     * Apply impulse to the ball.
     * @param direction direction of impulse
     * @param power power of impulse in kg-m/s
     */
    public void applyImpulse(Vector2 direction, float power) {
        Vector2 impulse = direction.cpy().nor().scl(power);
        getBody().applyLinearImpulse(impulse, getBody().getPosition(), true);
    }

    /**
     * Notify the observers the ball is pocketed.
     * @param pocket the pocket
     */
    // Otherwise we can't use a for each loop
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public void pocket(Pocket pocket) {
        // Notify the observers the cue ball is pocketed
        for (BallObserver observer : observers) {
            observer.potCueBall(this);
        }
    }
}
