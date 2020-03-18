package nl.tudelft.cse.sem.pool.gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import lombok.Getter;
import nl.tudelft.cse.sem.pool.creators.BodyCreator;
import nl.tudelft.cse.sem.pool.matchstate.BallObserver;
import nl.tudelft.cse.sem.pool.matchstate.Team;

public class NumberBall extends Ball {

    // All balls below 8 will be solid, above will be striped
    public static final int TEAM_BOUNDARY = 8;
    private @Getter transient int number;
    
    /**
     * Constructs the ball and sets up the properties for Box2d.
     *
     * @param bodyCreator factory creating the body for physics
     * @param radius      radius of ball
     * @param sprite      sprite ball has
     */
    public NumberBall(BodyCreator bodyCreator, float radius, Sprite sprite, int number) {
        super(bodyCreator, radius, sprite);
        this.number = number;
        getBody().setUserData(this);
    }

    /**
     * Notify the observers the ball is pocketed.
     * @param pocket the pocket
     */
    // otherwise this disallows me to use an iterator
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public void pocket(Pocket pocket) {
        if (number == TEAM_BOUNDARY) {
            // If the number is equal to the team boundary (8), 
            // notify the observer the black ball is potted
            for (BallObserver observer : observers) {
                observer.potBlackBall(this);
            }
        } else {
            // Else, just a regular number ball is potted
            for (BallObserver observer : observers) {
                observer.potNumberBall(this);
            }
        }
    }

    /**
     * Returns the team of the ball.
     * @return the team of the ball
     */
    public Team getTeam() {
        if (this.number < TEAM_BOUNDARY) {
            return Team.SOLID;
        } else if (this.number > TEAM_BOUNDARY) {
            return Team.STRIPES;
        } else {
            return Team.UNDETERMINED;
        }
    }
}
