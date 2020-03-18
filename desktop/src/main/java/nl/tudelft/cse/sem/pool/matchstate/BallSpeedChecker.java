package nl.tudelft.cse.sem.pool.matchstate;

import java.util.List;
import lombok.AllArgsConstructor;
import nl.tudelft.cse.sem.pool.gameobjects.Ball;
import nl.tudelft.cse.sem.pool.gameobjects.CueBall;
import nl.tudelft.cse.sem.pool.gameobjects.NumberBall;

@AllArgsConstructor
public class BallSpeedChecker {

    private static final float MIN_SPEED = 0.005f;

    private CueBall cueBall;
    private List<NumberBall> numberBalls;

    /**
     * Checks if all the number balls on the table have stopped moving.
     * @return true if all the balls are not moving anymore, false otherwise
     */
    //Otherwise it is not possible to use the for each loop
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public boolean areBallsStopped() {
        for (NumberBall b : numberBalls) {
            if (isMoving(b)) {
                return false;
            }
        }
        return isCueBallStopped();
    }

    /**
     * Checks if the cue ball has stopped.
     * @return if cue ball has stopped
     */
    public boolean isCueBallStopped() {
        return !isMoving(cueBall);
    }

    /**
     * Checks if ball is moving.
     * @param ball ball
     * @return true if ball is moving, else false
     */
    public boolean isMoving(Ball ball) {
        return ball.getBody().getLinearVelocity().len() >= MIN_SPEED;
    }

}
