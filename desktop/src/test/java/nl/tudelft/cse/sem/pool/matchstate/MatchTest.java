package nl.tudelft.cse.sem.pool.matchstate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.cse.sem.pool.creators.BodyCreator;
import nl.tudelft.cse.sem.pool.gameobjects.CueBall;
import nl.tudelft.cse.sem.pool.gameobjects.NumberBall;
import nl.tudelft.cse.sem.pool.gameobjects.TimeLimit;
import nl.tudelft.cse.sem.pool.gui.PoolGame;
import nl.tudelft.cse.sem.pool.gui.Sounds;
import nl.tudelft.cse.sem.pool.gui.StateHandler;
import nl.tudelft.cse.sem.pool.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MatchTest {

    transient Sprite sprite;
    transient World world;
    transient NumberBall ball1;
    transient NumberBall ball2;
    transient NumberBall ball3;
    transient NumberBall ball4;
    transient NumberBall ball5;
    transient NumberBall ball6;
    transient NumberBall ball7;
    transient NumberBall ball10;
    transient NumberBall ball13;
    transient CueBall cueBall;
    private transient Match match;
    private transient Player player1;
    private transient Player player2;
    private transient Sounds sounds;
    
    @BeforeEach
    void setUp() {
        world = new World(new Vector2(0,0), true);
        sprite = new Sprite();
        ball1 = new NumberBall(new BodyCreator(world), 1f, sprite, 1);
        ball2 = new NumberBall(new BodyCreator(world), 1f, sprite, 2);
        ball3 = new NumberBall(new BodyCreator(world), 1f, sprite, 3);
        ball4 = new NumberBall(new BodyCreator(world), 1f, sprite, 4);
        ball5 = new NumberBall(new BodyCreator(world), 1f, sprite, 5);
        ball6 = new NumberBall(new BodyCreator(world), 1f, sprite, 6);
        ball7 = new NumberBall(new BodyCreator(world), 1f, sprite, 7);
        ball10 = new NumberBall(new BodyCreator(world), 1f, sprite, 10);
        ball13 = new NumberBall(new BodyCreator(world), 1f, sprite, 13);
        cueBall = Mockito.mock(CueBall.class, Mockito.RETURNS_DEEP_STUBS);
        List<NumberBall> remainingBalls = new ArrayList<>();
        remainingBalls.add(ball1);
        remainingBalls.add(ball2);
        remainingBalls.add(ball3);
        remainingBalls.add(ball4);
        remainingBalls.add(ball5);
        remainingBalls.add(ball6);
        remainingBalls.add(ball7);
        remainingBalls.add(ball10);
        remainingBalls.add(ball13);
        User user1 = Mockito.mock(User.class);
        User user2 = Mockito.mock(User.class);
        player1 = new Player(user1);
        player2 = new Player(user2);
        sounds = Mockito.mock(Sounds.class);
        match = new Match(player1, player2, Mockito.mock(TimeLimit.class), sounds);
        StateHandler stateHandler = Mockito.mock(StateHandler.class);
        match.setRemainingBalls(remainingBalls);
        match.setCueBall(cueBall);
        match.setStateHandler(stateHandler);
    }
    
    @Test
    void constructorTest() {
        assertNotNull(match);
        assertEquals(State.SETUP, match.getState());
    }
    
    @Test
    void startPlayer1Test() {
        match.setFirstTurn(0.2);
        match.start();
        assertEquals(State.PLAYER1_TURN, match.getState());
    }

    @Test
    void startPlayer2Test() {
        match.setFirstTurn(0.8);
        match.start();
        assertEquals(State.PLAYER2_TURN, match.getState());
    }

    @Test
    void getPlayer1() {
        match.setFirstTurn(0.2);
        match.start();
        assertEquals(player1, match.getPlayer());
    }

    @Test
    void getPlayer1AfterGame() {
        match.setFirstTurn(0.8);
        match.start();
        match.potBlackBall(Mockito.mock(NumberBall.class));
        assertEquals(player1, match.getPlayer());
    }


    @Test
    void getPlayer2() {
        match.setFirstTurn(0.8);
        match.start();
        assertEquals(player2, match.getPlayer());
    }

    @Test
    void getPlayer2AfterGame() {
        match.setFirstTurn(0.2);
        match.start();
        match.potBlackBall(Mockito.mock(NumberBall.class));
        assertEquals(player2, match.getPlayer());
    }

    @Test
    void getNoPlayer() {
        assertNull(match.getPlayer());
    }

    @Test
    void getOpponent1() {
        match.setFirstTurn(0.2);
        match.start();
        assertEquals(player2, match.getOpponent());
    }

    @Test
    void getOpponent2() {
        match.setFirstTurn(0.8);
        match.start();
        assertEquals(player1, match.getOpponent());
    }

    @Test
    void getNoOpponent() {
        assertNull(match.getOpponent());
    }

    @Test
    void potSolidBallFirst_Player1() {
        match.setFirstTurn(0.2);
        match.start();

        match.potNumberBall(ball3);

        assertEquals(Team.SOLID, player1.team);
        assertEquals(Team.STRIPES, player2.team);
        assertEquals(State.PLAYER1_TURN, match.getState());
    }


    @Test
    void potStripeBallFirst_Player1() {
        match.setFirstTurn(0.2);
        match.start();

        match.potNumberBall(ball10);

        assertEquals(Team.STRIPES, player1.team);
        assertEquals(Team.SOLID, player2.team);
        assertEquals(State.PLAYER1_TURN, match.getState());
    }


    @Test
    void potSolidBallFirst_Player2() {
        match.setFirstTurn(0.8);
        match.start();

        match.potNumberBall(ball3);

        assertEquals(Team.STRIPES, player1.team);
        assertEquals(Team.SOLID, player2.team);
        assertEquals(State.PLAYER2_TURN, match.getState());
    }


    @Test
    void potStripeBallFirst_Player2() {
        match.setFirstTurn(0.8);
        match.start();

        match.potNumberBall(ball10);

        assertEquals(Team.SOLID, player1.team);
        assertEquals(Team.STRIPES, player2.team);
        assertEquals(State.PLAYER2_TURN, match.getState());
    }

    @Test
    void potSecondBall_Right_Player1() {
        match.setFirstTurn(0.2);
        match.start();

        match.potNumberBall(ball3);
        match.potNumberBall(ball4);
        assertEquals(5, player1.remainingBalls.size());
        assertEquals(2, player2.remainingBalls.size());
    }

    @Test
    void potSecondBall_Wrong_Player1() {
        match.setFirstTurn(0.2);
        match.start();

        match.potNumberBall(ball3);
        match.potNumberBall(ball10);
        assertEquals(player1.remainingBalls.size(), 6);
        assertEquals(player2.remainingBalls.size(), 1);
    }

    @Test
    void potSecondBall_Right_Player2() {
        match.setFirstTurn(0.8);
        match.start();

        match.potNumberBall(ball10);
        match.potNumberBall(ball13);
        assertEquals(player1.remainingBalls.size(), 7);
        assertEquals(player2.remainingBalls.size(), 0);
    }

    @Test
    void potSecondBall_Wrong_Player2() {
        match.setFirstTurn(0.8);
        match.start();

        match.potNumberBall(ball10);
        match.potNumberBall(ball3);
        assertEquals(player1.remainingBalls.size(), 6);
        assertEquals(player2.remainingBalls.size(), 1);
    }

    @Test
    void potCueBall() {
        match.setFirstTurn(0.2);
        match.start();

        match.potCueBall(new CueBall(new BodyCreator(world), 1f, sprite));
        assertTrue(match.isCueBallPot());
    }

    @Test
    void newTurnFoul_NoBallHit() {
        match.setFirstTurn(0.2);
        match.start();

        match.endHit();

        assertEquals(State.PLAYER2_TURN, match.getState());
        assertEquals(TurnState.PLACE, match.getTurnState());
    }


    @Test
    void newTurnFoul_WrongBallHit() {
        NumberBall blackBall = new NumberBall(new BodyCreator(world), 1f, sprite, 8);
        match.getRemainingBalls().add(blackBall);

        match.setFirstTurn(0.2);
        match.start();

        match.newHit();
        match.getWorldContactListener().cueBallHits.add(ball3);
        match.potNumberBall(ball3);
        match.endHit();
        match.newHit();
        match.getWorldContactListener().getCueBallHits().add(ball10);
        match.endHit();

        assertEquals(State.PLAYER2_TURN, match.getState());
        assertEquals(TurnState.PLACE, match.getTurnState());
    }

    @Test
    void noFoulUndetermined() {
        match.setFirstTurn(0.2);
        match.start();

        match.getWorldContactListener().getCueBallHits().add(ball10);
        match.endHit();

        assertEquals(State.PLAYER2_TURN, match.getState());
        assertEquals(TurnState.CUE, match.getTurnState());
    }

    @Test
    void newTurnExtra() {
        match.setFirstTurn(0.2);
        match.start();

        match.potNumberBall(ball3);
        match.getWorldContactListener().getCueBallHits().add(ball3);
        match.endHit();

        assertEquals(State.PLAYER1_TURN, match.getState());
        assertEquals(TurnState.CUE, match.getTurnState());
    }

    @Test
    void foulBall() {
        match.setFirstTurn(0.2);
        match.start();

        match.ballShot();
        assertEquals(TurnState.WAIT, match.getTurnState());
        match.endHit();
        assertEquals(TurnState.PLACE, match.getTurnState());
        match.ballPlaced();
        assertEquals(TurnState.CUE, match.getTurnState());
    }

    @Test
    void checkNothing() {
        match.setFirstTurn(0.2);
        match.start();

        match.check();
        assertEquals(TurnState.CUE, match.getTurnState());
    }

    @Test
    void checkBallsStopped() {
        Mockito.when(cueBall.getBody().getLinearVelocity().len()).thenReturn(0f);
        match.setFirstTurn(0.2);
        match.start();

        match.ballShot();
        match.check();
        assertEquals(State.PLAYER2_TURN, match.getState());
    }

    @Test
    void checkBallsMoving() {
        Mockito.when(cueBall.getBody().getLinearVelocity().len()).thenReturn(1f);
        match.setFirstTurn(0.2);
        match.start();

        match.ballShot();
        match.check();
        assertEquals(State.PLAYER1_TURN, match.getState());
    }

    @Test
    void endGame() {
        match.setState(State.PLAYER1_WON);
        match.endGame();
        Mockito.verify(sounds).playYouWin();
    }

    @Test
    void potBlackBall_Win_P1() {
        NumberBall blackBall = new NumberBall(new BodyCreator(world), 1f, sprite, 8);
        match.getRemainingBalls().add(blackBall);

        match.setFirstTurn(0.2);
        match.start();

        match.potNumberBall(ball3);
        match.potNumberBall(ball1);
        match.potNumberBall(ball2);
        match.potNumberBall(ball4);
        match.potNumberBall(ball5);
        match.potNumberBall(ball6);
        match.potNumberBall(ball7);
        match.newHit();
        assertEquals(1, player1.remainingBalls.size());
        assertTrue(player1.remainingBalls.contains(blackBall));
        match.getWorldContactListener().getCueBallHits().add(blackBall);
        match.potBlackBall(blackBall);
        assertEquals(State.PLAYER1_WON, match.getState());
    }

    @Test
    void potBlackBall_Win_P2() {
        NumberBall blackBall = new NumberBall(new BodyCreator(world), 1f, sprite, 8);
        match.getRemainingBalls().add(blackBall);

        match.setFirstTurn(0.8);
        match.start();

        match.potNumberBall(ball3);
        match.potNumberBall(ball1);
        match.potNumberBall(ball2);
        match.potNumberBall(ball4);
        match.potNumberBall(ball5);
        match.potNumberBall(ball6);
        match.potNumberBall(ball7);
        match.newHit();
        assertEquals(1, player2.remainingBalls.size());
        assertTrue(player2.remainingBalls.contains(blackBall));
        match.getWorldContactListener().getCueBallHits().add(blackBall);
        match.potBlackBall(blackBall);
        assertEquals(State.PLAYER2_WON, match.getState());
    }

    @Test
    void blackBall_AssignByOpponent() {
        NumberBall blackBall = new NumberBall(new BodyCreator(world), 1f, sprite, 8);
        match.getRemainingBalls().add(blackBall);

        match.setFirstTurn(0.2);
        match.start();

        match.potNumberBall(ball3);
        match.potNumberBall(ball1);
        match.potNumberBall(ball2);
        match.potNumberBall(ball4);
        match.potNumberBall(ball5);
        match.potNumberBall(ball6);
        match.getWorldContactListener().getCueBallHits().add(ball6);
        match.endHit();
        match.newHit();
        assertEquals(1, player1.remainingBalls.size());
        assertFalse(player1.remainingBalls.contains(blackBall));
        assertEquals(player1, match.getPlayer());
        match.getWorldContactListener().getCueBallHits().add(ball7);
        match.endHit();
        match.newHit();
        assertEquals(player2, match.getPlayer());

        match.potNumberBall(ball7);

        assertEquals(1, player1.remainingBalls.size());
        assertTrue(player1.remainingBalls.contains(blackBall));
    }

    @Test
    void blackBall_Lost() {
        NumberBall blackBall = new NumberBall(new BodyCreator(world), 1f, sprite, 8);
        match.getRemainingBalls().add(blackBall);

        match.setFirstTurn(0.2);
        match.start();

        match.potBlackBall(blackBall);
        assertEquals(State.PLAYER2_WON, match.getState());
    }
}
