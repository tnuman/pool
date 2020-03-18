package nl.tudelft.cse.sem.pool.matchstate;

import nl.tudelft.cse.sem.pool.gameobjects.CueBall;
import nl.tudelft.cse.sem.pool.gameobjects.NumberBall;

public interface BallObserver {

    /**
     * Event for potting a number ball.
     * @param ball number ball
     */
    void potNumberBall(NumberBall ball);

    /**
     * Event for potting the cue ball.
     * @param ball cue ball
     */
    void potCueBall(CueBall ball);

    /**
     * Event for potting the black ball.
     * @param blackBall the black ball
     */
    void potBlackBall(NumberBall blackBall);
    
}
