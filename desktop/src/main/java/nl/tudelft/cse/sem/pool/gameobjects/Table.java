package nl.tudelft.cse.sem.pool.gameobjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.cse.sem.pool.gui.Sprites;

public class Table extends Actor {

    private transient List<Wall> walls;
    private transient List<Pocket> pockets;
    private transient float tableWidth;
    private transient float tableHeight;
    private transient Sprite sprite;
    
    /**
     * Initializes a new table.
     *
     * @param world         world for the elements to reside in
     * @param distanceY      vertical distance between the edge of the screen and the wall,
     *                       higher distanceY makes the board smaller.
     * @param distanceX      horizontal distance between the edge of the screen and the wall,
     *                       higher distanceX makes the board smaller.
     * @param extraBallSpace space needed for the balls to be able to pass through to the pockets,
     *                       the higher, the bigger the opening for the balls to pass through
     * @param radius         radius of the pockets
     * @param screenHeight   height of the screen
     * @param screenWidth    width of the screen
     */
    public Table(World world, float distanceY, float distanceX, float extraBallSpace, float radius,
                 float screenWidth, float screenHeight, Sprites sprites, Stage stage) {
        walls = createWalls(world, distanceY, distanceX, extraBallSpace, screenWidth, screenHeight);
        pockets = createPockets(world, distanceY, distanceX, radius, screenWidth, screenHeight);
        tableHeight = screenHeight;
        tableWidth = screenWidth;
        sprite = sprites.getPoolTableSprite();
        addToStage(stage);
    }
    
    private List<Wall> createWalls(World world, float distanceY, float distanceX,
                                   float extraBallSpace, float screenWidth, float screenHeight) {
        List<Wall> walls = new ArrayList<>();
        //placing the walls at the right place on the board
        Wall bottomLeft = new Wall(world, distanceY + extraBallSpace, 0,
                screenWidth / 2 - distanceY - extraBallSpace * 2, distanceY);
        walls.add(bottomLeft);

        Wall bottomRight =
                new Wall(world, screenWidth / 2 + extraBallSpace, 0,
                        screenWidth / 2 - distanceY - extraBallSpace * 2, distanceY);
        walls.add(bottomRight);

        Wall left = new Wall(world, 0, distanceY + extraBallSpace * 1.5f,
                distanceX, screenHeight - (distanceY * 2) - extraBallSpace * 3);
        walls.add(left);

        Wall topLeft = new Wall(world, distanceY + extraBallSpace,
                screenHeight - distanceY,
                screenWidth / 2 - distanceY - extraBallSpace * 2, distanceY);
        walls.add(topLeft);

        Wall topRight = new Wall(world, screenWidth / 2 + extraBallSpace,
                screenHeight - distanceY,
                screenWidth / 2 - distanceY - extraBallSpace * 2, distanceY);
        walls.add(topRight);

        Wall right = new Wall(world, screenWidth - distanceX,
                distanceY + extraBallSpace * 1.5f,
                distanceX, screenHeight - (distanceY * 2) - extraBallSpace * 3);
        walls.add(right);
        return walls;
    }
    
    private List<Pocket> createPockets(World world, float distanceY, float distanceX, float radius,
                                       float screenWidth, float screenHeight) {
        List<Pocket> pockets = new ArrayList<>();

        //placing the pockets at the right place on the board
        Pocket topLeftCorner =
                new Pocket(world, distanceX, screenHeight - distanceY, radius);
        pockets.add(topLeftCorner);

        Pocket topMiddle = new Pocket(world, screenWidth / 2,
                screenHeight - distanceY + radius, radius);
        pockets.add(topMiddle);

        Pocket topRightCorner = new Pocket(world, screenWidth - distanceX,
                screenHeight - distanceY, radius);
        pockets.add(topRightCorner);

        Pocket bottomLeftCorner =
                new Pocket(world, distanceX, distanceY, radius);
        pockets.add(bottomLeftCorner);

        Pocket bottomMiddle =
                new Pocket(world, screenWidth / 2, distanceY - radius, radius);
        pockets.add(bottomMiddle);

        Pocket bottomRightCorner =
                new Pocket(world, screenWidth - distanceX, distanceY, radius);
        pockets.add(bottomRightCorner);
        return pockets;
    }
    
    /**
     * Add all actors to the stage.
     *
     * @param stage the stage where the actors reside in
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    //Just complains about using for each loop
    //Should find out how to suppress this rule in build.gradle
    private void addToStage(Stage stage) {
        for (Wall w : walls) {
            stage.addActor(w);
        }
        for (Pocket p : pockets) {
            stage.addActor(p);
        }
        stage.addActor(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sprite, 0, 0, tableWidth, tableHeight);
    }
}
