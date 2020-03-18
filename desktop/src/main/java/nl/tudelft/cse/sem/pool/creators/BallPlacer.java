package nl.tudelft.cse.sem.pool.creators;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import nl.tudelft.cse.sem.pool.gameobjects.Ball;
import nl.tudelft.cse.sem.pool.gui.Sprites;
import nl.tudelft.cse.sem.pool.matchstate.BallObserver;

public abstract class BallPlacer {

    public static final float RADIUS = 0.04f;
    transient Sprites sprites;
    transient BodyCreator bodyCreator;

    /**
     * Constructs the ball factory.
     * @param sprites sprites instance
     * @param bodyCreator factory creating the bodies for physics
     */
    public BallPlacer(Sprites sprites, BodyCreator bodyCreator) {
        this.sprites = sprites;
        this.bodyCreator = bodyCreator;
    }

    //Abstract factory method
    public abstract Ball createBall();

    /**
     * Creates a ball and places it on the table.
     * @param position - the position to place the ball
     * @param match - the match that should observer the ball
     * @param stage - the stage the ball should be added to
     * @return the placed ball
     */
    public Ball placeBall(Vector2 position, BallObserver match, Stage stage) {
        Ball ball = createBall();
        ball.setPosition(position.x, position.y);
        ball.addObserver(match);
        stage.addActor(ball);
        return ball;
    }
}
