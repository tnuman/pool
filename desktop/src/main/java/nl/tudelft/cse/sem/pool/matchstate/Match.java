package nl.tudelft.cse.sem.pool.matchstate;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nl.tudelft.cse.sem.pool.gameobjects.Ball;
import nl.tudelft.cse.sem.pool.gameobjects.CueBall;
import nl.tudelft.cse.sem.pool.gameobjects.NumberBall;
import nl.tudelft.cse.sem.pool.gameobjects.TimeLimit;
import nl.tudelft.cse.sem.pool.gameobjects.TimeListener;
import nl.tudelft.cse.sem.pool.gui.Sounds;
import nl.tudelft.cse.sem.pool.gui.StateHandler;

public class Match implements BallObserver {

    private transient Player player1;
    private transient Player player2;
    @Getter private transient TimeLimit timer;
    @Setter private transient StateHandler stateHandler;
    @Getter @Setter private transient State state;
    @Getter private transient TurnState turnState;
    private transient Sounds sounds;
    @Setter private transient double firstTurn;
    @Getter transient WorldContactListener worldContactListener;
    @Getter private transient boolean assignedTurn;
    @Getter private transient boolean cueBallPot;
    @Setter private transient CueBall cueBall;
    @Getter @Setter private transient List<NumberBall> remainingBalls;
    @Getter @Setter private transient List<Ball> newlyPotted;
    private transient PlayerManager playerManager;
    private transient String info;
    private transient List<NumberBall> pottedWithHit;

    /**
     * Creates a new match with to players.
     *
     * @param player1 the first player
     * @param player2 the second player
     */
    public Match(Player player1, Player player2, TimeLimit timer, Sounds sounds) {
        this.player1 = player1;
        this.player2 = player2;
        this.state = State.SETUP;
        this.turnState = TurnState.CUE;
        this.sounds = sounds;
        firstTurn = Math.random();
        newlyPotted = new ArrayList<>();
        pottedWithHit = new ArrayList<>();
        worldContactListener = new WorldContactListener(sounds);
        playerManager = new PlayerManager(player1, player2, remainingBalls);
        assignedTurn = false;
        cueBallPot = false;
        this.timer = timer;
        timer.addListener(new TimeListener() {
            @Override
            public void stopped() {
                System.out.println("TIME'S UP");
                endHit();
            }
        });
        info = "Please wait for the game to start...";
    }

    /**
     * Start the match, giving a random player the first turn.
     */
    public void start() {
        state = firstTurn < 0.5 ? State.PLAYER1_TURN : State.PLAYER2_TURN;
        timer.start();
        playerManager.setRemainingBalls(remainingBalls);
        System.out.println(getInfo());
    }

    /**
     * Check method called every frame to check things to do with the state of the game.
     */
    public void check() {
        if (turnState.equals(TurnState.WAIT)) {
            BallSpeedChecker checker = new BallSpeedChecker(cueBall, remainingBalls);
            if (checker.areBallsStopped()) {
                endHit();
            }
        }
    }

    /**
     * Called when a ball is shot.
     */
    public void ballShot() {
        sounds.playPoolShot();
        switchTurnState(TurnState.WAIT);
        newHit();
    }

    /**
     * Called when a ball is placed.
     */
    public void ballPlaced() {
        switchTurnState(TurnState.CUE);
    }

    /**
     * Switch the turn to a new turnstate.
     * @param state turn state
     */
    public void switchTurnState(TurnState state) {
        turnState = state;
        stateHandler.updateInputMethod(state);
    }

    /**
     * Gets the current player who has the turn.
     *
     * @return the current player taking the turn
     */
    public Player getPlayer() {
        if (state.equals(State.PLAYER1_TURN) || state.equals(State.PLAYER1_WON)) {
            return player1;
        } else if (state.equals(State.PLAYER2_TURN)  || state.equals(State.PLAYER2_WON)) {
            return player2;
        }
        return null;
    }

    /**
     * Gets the current opponent who waits for the turn.
     *
     * @return the current player taking the turn
     */
    public Player getOpponent() {
        if (state.equals(State.PLAYER1_TURN)) {
            return player2;
        } else if (state.equals(State.PLAYER2_TURN)) {
            return player1;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void potNumberBall(NumberBall ball) {
        newlyPotted.add(ball);
        pottedWithHit.add(ball);
        remainingBalls.remove(ball);
        System.out.println("BALL POTTED: " + ball.getNumber());
        playerManager.potNumberBall(getPlayer(), getOpponent(), ball);
    }

    /**
     * {@inheritDoc}
     */
    public void potCueBall(CueBall ball) {
        cueBallPot = true;
        cueBall.release();
        cueBall.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    public void potBlackBall(NumberBall blackBall) {
        newlyPotted.add(blackBall);
        Player potter = getPlayer();
        if (potter.remainingBalls.contains(blackBall)) {
            potter.remainingBalls.remove(blackBall);
            potter.pottedBalls.add(blackBall);
            state = potter.equals(player1) ? State.PLAYER1_WON : State.PLAYER2_WON;
        } else {
            state = potter.equals(player2) ? State.PLAYER1_WON : State.PLAYER2_WON;
        }
        endGame();
    }

    /**
     * Ends the game.
     */
    public void endGame() {
        sounds.playYouWin();
        stateHandler.endGame();
    }

    /**
     * Does logic when hitting ball with cue.
     */
    public void newHit() {
        worldContactListener.setCueBallHits(new ArrayList<>());
        cueBallPot = false;
        pottedWithHit = new ArrayList<>();
        timer.stop();
    }

    /**
     * Does logic when hit has ended.
     */
    public void endHit() {
        getPlayer().numberOfTurns++;
        TurnChecker turnChecker = new TurnChecker(getPlayer(), assignedTurn, cueBallPot,
                pottedWithHit, worldContactListener.cueBallHits);
        if (turnChecker.switchTurn()) {
            state = state.reverse();
        }
        if (turnChecker.isFoul()) {
            cueBall.release();
            switchTurnState(TurnState.PLACE);
        } else {
            switchTurnState(TurnState.CUE);
        }
        assignedTurn = turnChecker.ballsAssigned();
        timer.start();
        System.out.println(getInfo());
    }

    /**
     * Get the number of turns of the current player.
     * @return the number of turns of the current player
     */
    public int getPlayerTurns() {
        return getPlayer().numberOfTurns;
    }
    
    /**
     * Get the current info about the match.
     * @return the current info about the match
     */
    public String getInfo() {
        return state + " - "
                + "Team: " + getPlayer().team + " | "
                + "Turn: " + Integer.toString(getPlayerTurns() + 1);
    }

    /**
     * Get the number of balls of the current player.
     * @return the number of of the current player
     */
    public int getPlayerBallsSize() {
        return getPlayer().remainingBalls.size();
    }
}
