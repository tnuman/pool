package nl.tudelft.cse.sem.pool.matchstate;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import nl.tudelft.cse.sem.pool.gameobjects.Ball;
import nl.tudelft.cse.sem.pool.gameobjects.CueBall;
import nl.tudelft.cse.sem.pool.gameobjects.NumberBall;
import nl.tudelft.cse.sem.pool.gameobjects.Pocket;
import nl.tudelft.cse.sem.pool.gameobjects.Wall;
import nl.tudelft.cse.sem.pool.gui.Sounds;

public class WorldContactListener implements ContactListener {

    @Getter @Setter List<NumberBall> cueBallHits = new ArrayList<>();
    private transient Sounds sounds;

    public WorldContactListener(Sounds sounds) {
        this.sounds = sounds;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Object objectA = fixtureA.getBody().getUserData();
        Object objectB = fixtureB.getBody().getUserData();

        if (objectA instanceof Pocket) {
            ballInPocket((Ball) objectB, (Pocket) objectA);
        } else if (objectB instanceof Pocket) {
            ballInPocket((Ball) objectA, (Pocket) objectB);
        }

        if (objectA instanceof Wall) {
            wallHit((Ball) objectB, (Wall) objectA);
        } else if (objectB instanceof Wall) {
            wallHit((Ball) objectA, (Wall) objectB);
        }

        if (objectA instanceof Ball && objectB instanceof Ball) {
            ballsHit((Ball) objectA, (Ball) objectB);
        }
    }

    /**
     * Event for pocketing ball in pocket.
     * @param ball ball
     * @param pocket pocket
     */
    public void ballInPocket(Ball ball, Pocket pocket) {
        sounds.playPocket();
        ball.pocket(pocket);
    }

    /**
     * Event for a ball hitting each a wall.
     * @param ball a ball
     * @param wall a wall
     */
    public void wallHit(Ball ball, Wall wall) {
        sounds.playWallsHit();
    }

    /**
     * Event for two balls hitting each other.
     * @param ballA one ball
     * @param ballB other ball
     */
    public void ballsHit(Ball ballA, Ball ballB) {
        sounds.playBallsHit();
        if (ballA instanceof CueBall) {
            cueBallHits.add((NumberBall) ballB);
        } else if (ballB instanceof CueBall) {
            cueBallHits.add((NumberBall) ballA);
        }
    }

    /* We need to @Override the methods below to be able to implement the interface(s),
    Bodies can stay empty if we don't use them */

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
