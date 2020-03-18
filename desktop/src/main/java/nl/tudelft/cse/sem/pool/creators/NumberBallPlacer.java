package nl.tudelft.cse.sem.pool.creators;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nl.tudelft.cse.sem.pool.gameobjects.NumberBall;
import nl.tudelft.cse.sem.pool.gui.Sprites;
import nl.tudelft.cse.sem.pool.matchstate.BallObserver;

public class NumberBallPlacer extends BallPlacer {

    @Getter @Setter
    private transient int currentNumber;

    /**
     * Initiates a new number ball placer.
     * @param sprites the sprites for the number balls
     * @param bodyCreator the creater for the ball bodies
     * @param startNumber the number of the first ball that should be created
     */
    public NumberBallPlacer(Sprites sprites, BodyCreator bodyCreator, int startNumber) {
        super(sprites, bodyCreator);
        this.currentNumber = startNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override //Abstract factory method
    public NumberBall createBall() {
        return new NumberBall(bodyCreator, RADIUS, sprites.getBallSprite(currentNumber),
                currentNumber);
    }

    /**
     * Place the number balls randomly in the right position.
     * @param origin the global position of the collection of balls
     * @return list of number balls
     */
    //otherwise this disallows me to use an iterator
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public List<NumberBall> placeNumberBalls(Vector2 origin, BallObserver match, Stage stage) {
        List<Integer> ballOrder = createRandomOrder();
        List<NumberBall> result = new ArrayList<>();
        Iterator<Integer> iterator = ballOrder.iterator();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < i + 1; j++) {
                Vector2 position = determineNumberBallPosition(i, j);
                position.add(origin);
                this.currentNumber = iterator.next();
                result.add((NumberBall) placeBall(position, match, stage));
            }
        }
        return result;
    }

    /**
     * Create a random ball order according to the rules.
     */
    private List<Integer> createRandomOrder() {
        List<Integer> ballOrder = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            ballOrder.add(i);
        }
        Collections.shuffle(ballOrder);
        int onePos = ballOrder.indexOf(1);
        Collections.swap(ballOrder, 0, onePos);
        int eightPos = ballOrder.indexOf(8);
        Collections.swap(ballOrder, 4, eightPos);
        return ballOrder;
    }

    /**
     * Determine position of a ball.
     * @param x the column of the ball in the triangle setup (most left = column 0)
     * @param y the row of the ball in the triangle setup (highest = row 0)
     * @return the position of the ball
     */
    private Vector2 determineNumberBallPosition(int x, int y) {
        float posX = x * (float) Math.sqrt(3f) * RADIUS;
        float posY = x * RADIUS - 2 * y * RADIUS;
        return new Vector2(posX, posY);
    }
}
