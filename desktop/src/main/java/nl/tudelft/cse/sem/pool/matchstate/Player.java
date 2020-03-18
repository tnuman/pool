package nl.tudelft.cse.sem.pool.matchstate;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nl.tudelft.cse.sem.pool.gameobjects.NumberBall;
import nl.tudelft.cse.sem.pool.models.User;

public class Player {
    private transient User user;
    transient Team team;
    transient List<NumberBall> remainingBalls;
    transient List<NumberBall> pottedBalls;
    transient int numberOfTurns;

    /**
     * Creates a new Player.
     * @param user The user that 'controls' this player
     */
    public Player(User user) {
        this.user = user;
        remainingBalls = new ArrayList<NumberBall>();
        pottedBalls = new ArrayList<NumberBall>();
        team = Team.UNDETERMINED;
        numberOfTurns = 0;
    }
}
