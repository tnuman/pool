package nl.tudelft.cse.sem.pool.matchstate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.cse.sem.pool.gameobjects.NumberBall;
import nl.tudelft.cse.sem.pool.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TurnCheckerTest {

    transient TurnChecker checker;
    private transient List<NumberBall> pottedBalls;
    private transient List<NumberBall> hitBalls;

    @BeforeEach
    void setUp() {
        pottedBalls = new ArrayList<>();
        hitBalls = new ArrayList<>();
        Player player = new Player(Mockito.mock(User.class));
        player.team = Team.SOLID;
        checker = Mockito.spy(new TurnChecker(player, true,
                false, pottedBalls, hitBalls));
    }

    @Test
    void switchTurnFoal() {
        Mockito.when(checker.isFoul()).thenReturn(true);
        assertTrue(checker.switchTurn());
    }

    @Test
    void switchTurnNoPot() {
        Mockito.when(checker.isFoul()).thenReturn(false);
        assertTrue(checker.switchTurn());
    }

    @Test
    void switchTurnWrongPot() {
        Mockito.when(checker.isFoul()).thenReturn(false);
        NumberBall ball = Mockito.mock(NumberBall.class);
        pottedBalls.add(ball);
        Mockito.when(ball.getTeam()).thenReturn(Team.STRIPES);
        assertTrue(checker.switchTurn());
    }

    @Test
    void switchTurnWrongFalse() {
        Mockito.when(checker.isFoul()).thenReturn(false);
        NumberBall ball = Mockito.mock(NumberBall.class);
        pottedBalls.add(ball);
        Mockito.when(ball.getTeam()).thenReturn(Team.SOLID);
        assertFalse(checker.switchTurn());
    }

    @Test
    void isFoulNoHit() {
        assertTrue(checker.isFoul());
    }

    @Test
    void isFoulWrongTeam() {
        NumberBall ball = Mockito.mock(NumberBall.class);
        Mockito.when(ball.getTeam()).thenReturn(Team.STRIPES);
        hitBalls.add(ball);
        assertTrue(checker.isFoul());
    }

    @Test
    void isFoulNoAssignedTurn() {
        NumberBall ball = Mockito.mock(NumberBall.class);
        hitBalls.add(ball);
        checker.setAssignedTurn(false);
        assertFalse(checker.isFoul());
    }

    @Test
    void noFoulSameTeam() {
        NumberBall ball = Mockito.mock(NumberBall.class);
        Mockito.when(ball.getTeam()).thenReturn(Team.SOLID);
        hitBalls.add(ball);
        assertFalse(checker.isFoul());
    }

    @Test
    void isFoulCuePot() {
        checker.setCueBalPotted(true);
        assertTrue(checker.isFoul());
    }

    @Test
    void isFoulWrongTeamCuePot() {
        checker.setCueBalPotted(true);
        checker.setAssignedTurn(false);
        NumberBall ball = Mockito.mock(NumberBall.class);
        Mockito.when(ball.getTeam()).thenReturn(Team.STRIPES);
        hitBalls.add(ball);
        assertTrue(checker.isFoul());
    }

    @Test
    void ballsAssignedAlready() {
        assertTrue(checker.ballsAssigned());
    }

    @Test
    void ballsAssignedPotted() {
        checker.setAssignedTurn(false);
        NumberBall ball = Mockito.mock(NumberBall.class);
        pottedBalls.add(ball);
        assertTrue(checker.ballsAssigned());
    }

    @Test
    void noBallsAssigned() {
        checker.setAssignedTurn(false);
        assertFalse(checker.ballsAssigned());
    }
}