package nl.tudelft.cse.sem.pool.gameobjects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import nl.tudelft.cse.sem.pool.creators.BodyCreator;
import nl.tudelft.cse.sem.pool.matchstate.BallObserver;
import nl.tudelft.cse.sem.pool.matchstate.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class NumberBallTest extends BallTest {
    
    private transient NumberBall nball;

    @BeforeEach
    void setUp() {
        world = new World(new Vector2(0,0), true);
        sprite = new Sprite();
        ball = new NumberBall(new BodyCreator(world), 1f, sprite, 4);
        nball = (NumberBall) ball;
    }

    @Test
    void numberBallConstructorTest() {
        assertNotNull(ball);
        assertEquals(4, ((NumberBall) ball).getNumber());
    }

    @Test
    void pocketRegularNumberBallTest() {
        Pocket pocket = Mockito.mock(Pocket.class);
        BallObserver ballObserver = Mockito.mock(BallObserver.class);
        nball.addObserver(ballObserver);
        nball.pocket(pocket);
        Mockito.verify(ballObserver).potNumberBall(nball);
    }
    
    @Test
    void pocketBlackBallTest() {
        NumberBall blackBall = new NumberBall(new BodyCreator(world), 1f, sprite, 8);
        Pocket pocket = Mockito.mock(Pocket.class);
        BallObserver ballObserver = Mockito.mock(BallObserver.class);
        blackBall.addObserver(ballObserver);
        blackBall.pocket(pocket);
        Mockito.verify(ballObserver).potBlackBall(blackBall);
    }
    
    @Test
    void teamTestSolid() {
        assertEquals(Team.SOLID, nball.getTeam());
    }
    
    @Test
    void teamTestStripes() {
        NumberBall stripeBall = new NumberBall(new BodyCreator(world), 1f, sprite, 11);
        assertEquals(Team.STRIPES, stripeBall.getTeam());
    }

    @Test
    void teamTestBlack() {
        NumberBall blackBall = new NumberBall(new BodyCreator(world), 1f, sprite, 8);
        assertEquals(Team.UNDETERMINED, blackBall.getTeam());
    }
}