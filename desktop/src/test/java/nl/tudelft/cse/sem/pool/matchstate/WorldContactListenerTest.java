package nl.tudelft.cse.sem.pool.matchstate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import nl.tudelft.cse.sem.pool.gameobjects.Ball;
import nl.tudelft.cse.sem.pool.gameobjects.CueBall;
import nl.tudelft.cse.sem.pool.gameobjects.NumberBall;
import nl.tudelft.cse.sem.pool.gameobjects.Pocket;
import nl.tudelft.cse.sem.pool.gameobjects.Wall;
import nl.tudelft.cse.sem.pool.gui.Sounds;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class WorldContactListenerTest {

    transient Sounds sounds;
    transient WorldContactListener listener;
    transient Contact contact;
    transient Fixture fixtureA;
    transient Fixture fixtureB;

    @BeforeEach
    void setUp() {
        sounds = Mockito.mock(Sounds.class);
        listener = Mockito.spy(new WorldContactListener(sounds));
        contact = Mockito.mock(Contact.class);
        fixtureA = Mockito.mock(Fixture.class, Mockito.RETURNS_DEEP_STUBS);
        fixtureB = Mockito.mock(Fixture.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(contact.getFixtureA()).thenReturn(fixtureA);
        Mockito.when(contact.getFixtureB()).thenReturn(fixtureB);
    }

    @Test
    void beginContactBallPocket() {
        Ball ball = Mockito.mock(Ball.class);
        Pocket pocket = Mockito.mock(Pocket.class);
        Mockito.when(fixtureA.getBody().getUserData()).thenReturn(ball);
        Mockito.when(fixtureB.getBody().getUserData()).thenReturn(pocket);

        listener.beginContact(contact);
        Mockito.verify(listener).ballInPocket(ball, pocket);
        Mockito.verify(sounds).playPocket();
    }

    @Test
    void beginContactPocketBall() {
        Ball ball = Mockito.mock(Ball.class);
        Pocket pocket = Mockito.mock(Pocket.class);
        Mockito.when(fixtureA.getBody().getUserData()).thenReturn(pocket);
        Mockito.when(fixtureB.getBody().getUserData()).thenReturn(ball);

        listener.beginContact(contact);
        Mockito.verify(listener).ballInPocket(ball, pocket);
    }

    @Test
    void ballInPocket() {
        Ball ball = Mockito.mock(Ball.class);
        Pocket pocket = Mockito.mock(Pocket.class);
        listener.ballInPocket(ball, pocket);
        Mockito.verify(ball).pocket(pocket);
        Mockito.verify(sounds).playPocket();
    }

    @Test
    void ballsHit() {
        Ball ball1 = Mockito.mock(Ball.class);
        Ball ball2 = Mockito.mock(Ball.class);
        Mockito.when(fixtureA.getBody().getUserData()).thenReturn(ball1);
        Mockito.when(fixtureB.getBody().getUserData()).thenReturn(ball2);

        listener.beginContact(contact);
        Mockito.verify(listener).ballsHit(ball1, ball2);
        Mockito.verify(sounds).playBallsHit();
    }

    @Test
    void cueBallHitsNumberBall() {
        CueBall cueBall = Mockito.mock(CueBall.class);
        NumberBall ball = Mockito.mock(NumberBall.class);
        Mockito.when(fixtureA.getBody().getUserData()).thenReturn(cueBall);
        Mockito.when(fixtureB.getBody().getUserData()).thenReturn(ball);

        listener.beginContact(contact);
        Mockito.verify(listener).ballsHit(cueBall,ball);
        assertEquals(1, listener.getCueBallHits().size());
    }

    @Test
    void ballHitsCueBall() {
        CueBall cueBall = Mockito.mock(CueBall.class);
        NumberBall ball = Mockito.mock(NumberBall.class);
        Mockito.when(fixtureA.getBody().getUserData()).thenReturn(ball);
        Mockito.when(fixtureB.getBody().getUserData()).thenReturn(cueBall);

        listener.beginContact(contact);
        Mockito.verify(listener).ballsHit(ball,cueBall);
        assertEquals(1, listener.getCueBallHits().size());
    }

    @Test
    void checkUselessOverriddenMethodsNoCrash() {
        listener.endContact(contact);
        listener.postSolve(contact, Mockito.mock(ContactImpulse.class));
        listener.preSolve(contact, Mockito.mock(Manifold.class));
        assertNotNull(listener);
    }

    @Test
    void contact_Ball_Wall() {
        Ball ball = Mockito.mock(Ball.class);
        Wall wall = Mockito.mock(Wall.class);
        Mockito.when(fixtureA.getBody().getUserData()).thenReturn(ball);
        Mockito.when(fixtureB.getBody().getUserData()).thenReturn(wall);
        listener.beginContact(contact);

        Mockito.verify(listener, Mockito.times(1)).wallHit(ball,wall);
        Mockito.verify(sounds, Mockito.times(1)).playWallsHit();
    }

    @Test
    void contact_Wall_Ball() {
        Ball ball = Mockito.mock(Ball.class);
        Wall wall = Mockito.mock(Wall.class);
        Mockito.when(fixtureA.getBody().getUserData()).thenReturn(wall);
        Mockito.when(fixtureB.getBody().getUserData()).thenReturn(ball);
        listener.beginContact(contact);

        Mockito.verify(listener, Mockito.times(1)).wallHit(ball,wall);
        Mockito.verify(sounds, Mockito.times(1)).playWallsHit();
    }
}
