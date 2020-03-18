package nl.tudelft.cse.sem.pool.matchstate;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nl.tudelft.cse.sem.pool.gameobjects.NumberBall;

/**
 * Manages the relation player - number ball when such a ball is potted.
 */
public class PlayerManager {

    private transient Player player1;
    private transient Player player2;
    private transient boolean first;
    @Getter @Setter private transient List<NumberBall> remainingBalls;

    /**
     * Constructor for the class.
     * @param player1 player 1
     * @param player2 player 2
     * @param balls number balls
     */
    public PlayerManager(Player player1, Player player2, List<NumberBall> balls) {
        this.player1 = player1;
        this.player2 = player2;
        this.remainingBalls = balls;
        first = true;
    }

    /**
     * Called when a number ball is potted.
     * @param player potter
     * @param opponent opponent
     * @param ball potted ball
     */
    public void potNumberBall(Player player, Player opponent, NumberBall ball) {
        if (first) {
            firstPot(player, opponent, ball);
        } else {
            otherPot(ball);
        }
    }

    /**
     * Logic when it is the first pot.
     * @param player potter
     * @param opponent opponent
     * @param ball potted ball
     */
    private void firstPot(Player player, Player opponent, NumberBall ball) {
        assignTeams(player, opponent, ball);
        assignBalls();
        player.pottedBalls.add(ball);
        first = false;
    }

    /**
     * Logic when the pot is not the first pot.
     * @param ball potted ball
     */
    public void otherPot(NumberBall ball) {
        Player assignee = getAssignee(ball);
        assignee.pottedBalls.add(ball);
        assignee.remainingBalls.remove(ball);
        if (assignee.pottedBalls.size() == ball.TEAM_BOUNDARY - 1) {
            assignee.remainingBalls.add(getBlackBall());
        }
    }

    /**
     * Assign players to teams.
     * @param player potter
     * @param opponent opponent
     * @param ball potted ball
     */
    public void assignTeams(Player player, Player opponent, NumberBall ball) {
        player.team = ball.getTeam();
        opponent.team = ball.getTeam().reverse();
    }

    /**
     * Get person the ball is assigned to.
     * @param ball ball
     * @return player
     */
    public Player getAssignee(NumberBall ball) {
        if (ball.getTeam().equals(player1.team)) {
            return player1;
        } else {
            return player2;
        }
    }

    /**
     * Assigns the rest of the balls to the players.
     */
    // Otherwise we can't use a for each loop
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public void assignBalls() {
        for (NumberBall ball : remainingBalls) {
            if (ball.getNumber() != ball.TEAM_BOUNDARY) {
                if (ball.getTeam().equals(player1.team)) {
                    player1.remainingBalls.add(ball);
                } else {
                    player2.remainingBalls.add(ball);
                }
            }
        }
        System.out.println("PLAYER 1 - TEAM " + player1.team);
        for (NumberBall b : player1.remainingBalls) {
            System.out.println(b.getTeam() + " " + b.getNumber());
        }
        System.out.println("PLAYER 2 - TEAM " + player2.team);
        for (NumberBall b : player2.remainingBalls) {
            System.out.println(b.getTeam() + " " + b.getNumber());
        }
    }

    /**
     * Return the black ball of this game.
     * @return the black ball of this game
     */
    NumberBall getBlackBall() {
        for (int i = 0; i < remainingBalls.size(); i++) {
            NumberBall ball = remainingBalls.get(i);
            if (ball.getNumber() == NumberBall.TEAM_BOUNDARY) {
                return ball;
            }
        }
        return null;
    }
}
