package nl.tudelft.cse.sem.pool.creators;

import nl.tudelft.cse.sem.pool.gameobjects.CueBall;
import nl.tudelft.cse.sem.pool.gui.Sprites;

public class CueBallPlacer extends BallPlacer {

    /**
     * Initiates a new cue ball placer.
     * @param sprites - the sprites for the cue ball
     * @param bodyCreator - the creator for the body of the cue ball
     */
    public CueBallPlacer(Sprites sprites, BodyCreator bodyCreator) {
        super(sprites, bodyCreator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CueBall createBall() {
        return new CueBall(bodyCreator, RADIUS, sprites.getCueBall());
    }
}
