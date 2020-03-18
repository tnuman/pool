package nl.tudelft.cse.sem.pool.matchstate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.cse.sem.pool.creators.BodyCreator;
import nl.tudelft.cse.sem.pool.gameobjects.NumberBall;
import nl.tudelft.cse.sem.pool.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PlayerManagerTest {

    private transient PlayerManager manager;
    private transient Player player1;
    private transient Player player2;
    transient NumberBall ball5;
    transient NumberBall ball6;
    transient NumberBall ball7;
    transient NumberBall ball10;
    transient NumberBall ball13;

    @BeforeEach
    void setUp() {
        User user1 = Mockito.mock(User.class);
        User user2 = Mockito.mock(User.class);
        player1 = new Player(user1);
        player2 = new Player(user2);
        World world = new World(new Vector2(0,0), true);
        Sprite sprite = Mockito.mock(Sprite.class);
        ball5 = new NumberBall(new BodyCreator(world), 1f, sprite, 5);
        ball6 = new NumberBall(new BodyCreator(world), 1f, sprite, 6);
        ball7 = new NumberBall(new BodyCreator(world), 1f, sprite, 7);
        ball10 = new NumberBall(new BodyCreator(world), 1f, sprite, 10);
        ball13 = new NumberBall(new BodyCreator(world), 1f, sprite, 13);
        List<NumberBall> remainingBalls = new ArrayList<>();
        remainingBalls.add(ball5);
        remainingBalls.add(ball6);
        remainingBalls.add(ball7);
        remainingBalls.add(ball10);
        remainingBalls.add(ball13);
        manager = Mockito.spy(new PlayerManager(player1, player2, remainingBalls));
    }

    @Test
    void otherPot() {
        player1.team = Team.SOLID;
        player1.team = Team.STRIPES;
        manager.assignBalls();

        Mockito.when(manager.getAssignee(ball6)).thenReturn(player1);
        manager.otherPot(ball6);
        assertEquals(1, player1.pottedBalls.size());
        assertEquals(ball6, player1.pottedBalls.get(0));
        assertEquals(2, player1.remainingBalls.size());
    }

    @Test
    void potFirstBall() {
        manager.potNumberBall(player1, player2, ball6);
        assertEquals(1, player1.pottedBalls.size());
        Mockito.verify(manager).assignBalls();
        Mockito.verify(manager, Mockito.never()).otherPot(ball6);
    }

    @Test
    void potSecondBall() {
        manager.potNumberBall(player1, player2, ball6);
        manager.potNumberBall(player1, player2, ball10);
        Mockito.verify(manager).otherPot(ball10);
    }

    @Test
    // Otherwise we can't use loop to get to 6
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    void otherPotBlack() {
        NumberBall ball = Mockito.mock(NumberBall.class);
        for (int i = 0; i < 6; i++) {
            player1.pottedBalls.add(ball);
        }

        player1.team = Team.SOLID;
        player1.team = Team.STRIPES;
        manager.assignBalls();

        NumberBall blackBall = Mockito.mock(NumberBall.class);
        Mockito.when(manager.getBlackBall()).thenReturn(blackBall);
        Mockito.when(manager.getAssignee(ball6)).thenReturn(player1);
        manager.otherPot(ball6);
        assertTrue(player1.remainingBalls.contains(blackBall));
        Mockito.verify(manager).getBlackBall();
    }

    @Test
    void assignTeams() {
        manager.assignTeams(player1, player2, ball10);
        assertEquals(Team.STRIPES, player1.team);
        assertEquals(Team.SOLID, player2.team);
    }

    @Test
    void assignTeamsReverse() {
        manager.assignTeams(player2, player1, ball10);
        assertEquals(Team.SOLID, player1.team);
        assertEquals(Team.STRIPES, player2.team);
    }

    @Test
    void getAssignee() {
        player1.team = Team.SOLID;
        player2.team = Team.STRIPES;
        assertEquals(player1, manager.getAssignee(ball5));
        assertEquals(player1, manager.getAssignee(ball7));
        assertEquals(player2, manager.getAssignee(ball10));
    }

    @Test
    void assignBalls() {
        player1.team = Team.SOLID;
        player2.team = Team.STRIPES;
        manager.assignBalls();
        assertEquals(3, player1.remainingBalls.size());
        assertEquals(2, player2.remainingBalls.size());
        assertTrue(player1.remainingBalls.contains(ball6));
        assertTrue(player2.remainingBalls.contains(ball13));
    }

    @Test
    void getBlackBall() {
        NumberBall blackBall = Mockito.mock(NumberBall.class);
        manager.getRemainingBalls().add(blackBall);
        Mockito.when(blackBall.getNumber()).thenReturn(8);
        assertEquals(manager.getBlackBall(), blackBall);
    }

    @Test
    void getBlackBallNull() {
        NumberBall blackBall = Mockito.mock(NumberBall.class);
        manager.getRemainingBalls().add(blackBall);
        Mockito.when(blackBall.getNumber()).thenReturn(5);
        assertNull(manager.getBlackBall());
    }
}