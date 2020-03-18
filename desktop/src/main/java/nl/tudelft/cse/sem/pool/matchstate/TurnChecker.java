package nl.tudelft.cse.sem.pool.matchstate;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Setter;
import nl.tudelft.cse.sem.pool.gameobjects.NumberBall;

/**
 * Checks logic behind switching turns, e.g. fouls, switching.
 */
@AllArgsConstructor
public class TurnChecker {

    private Player potter;
    @Setter private boolean assignedTurn;
    @Setter private boolean cueBalPotted;
    private List<NumberBall> pottedBalls;
    private List<NumberBall> hitBalls;

    /**
     * Checks if turn should be switched.
     * @return true if switched
     */
    public boolean switchTurn() {
        return isFoul() || checkPotted();
    }

    /**
     * Checks if the hit was a foul.
     * @return true if foul
     */
    public boolean isFoul() {
        return checkHit() || cueBalPotted;
    }

    /**
     * Checks if next hit or turn the teams are determined.
     * @return true if determined
     */
    public boolean ballsAssigned() {
        return assignedTurn || !pottedBalls.isEmpty();
    }

    /**
     * Checks if turn should switch because of potted number balls.
     * @return true if foul
     */
    //Otherwise it is not possible to use the for each loop
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    private boolean checkPotted() {
        if (pottedBalls.isEmpty()) {
            return true;
        }
        for (NumberBall b : pottedBalls) {
            if (!potter.team.equals(b.getTeam())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there is a foul with hitting.
     * @return true if foul
     */
    private boolean checkHit() {
        if (hitBalls.isEmpty()) {
            return true;
        }
        if (assignedTurn && potter.remainingBalls.size() == 1
                && potter.remainingBalls.contains(hitBalls.get(0))
                && potter.remainingBalls.get(0).getNumber() == 8) {
            return false;
        }
        if (assignedTurn && !hitBalls.get(0).getTeam().equals(potter.team)) {
            return true;
        }
        return false;
    }

}
