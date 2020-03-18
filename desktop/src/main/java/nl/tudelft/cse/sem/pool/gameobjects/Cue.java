package nl.tudelft.cse.sem.pool.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.Getter;
import lombok.Setter;
import nl.tudelft.cse.sem.pool.gui.PositionProjector;
import nl.tudelft.cse.sem.pool.gui.Sprites;

public class Cue extends Actor {

    private static final float WIDTH = 0.03f;
    private static final float OFFSET = 0.2f;
    private static final float MAX_DRAG = 3.f;
    private static final float MIN_DRAG = 0.15f;
    private @Getter transient float drag = 0;
    private transient Sprite sprite;
    private @Getter transient Vector2 ballPos;
    private transient CueBall ball;

    /**
     * Initiates a new Cue object.
     * @param ball - the ball the cue hits
     * @param sprites - the sprite for the cue
     */
    public Cue(CueBall ball, Sprites sprites) {
        this.ball = ball;
        this.ballPos = ball.getBody().getPosition();
        this.sprite = sprites.getCueSprite();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        float dragOffset = drag * OFFSET + OFFSET;
        Vector2 mouse = new PositionProjector(getStage()).mouseWorldPosition();
        batch.draw(sprite, ballPos.x + dragOffset, ballPos.y - WIDTH / 2,
                -dragOffset, WIDTH / 2, sprite.getWidth() / sprite.getHeight() * WIDTH,
                WIDTH, 0.5f, 1.f, calcRotation(mouse));
    }

    /**
     * Calculates the rotation of the cue in degrees.
     *
     * @return float representing the rotation
     */
    private float calcRotation(Vector2 mouse) {
        Vector2 dir = mouse.sub(ballPos);
        return (float) Math.toDegrees(Math.atan2(dir.y, dir.x));
    }

    /**
     * Changes the drag of the cue by the given change.
     * The value will be clamped between 0 and maxDrag
     * If the player is moving the cue to the ball and the newDrag < minDrag,
     * the cue position is reset
     *
     * @param change change that will be added to the drag
     */
    public void changeDrag(float change) {
        float newDrag = Math.max(0, Math.min(drag + change, MAX_DRAG));
        if (drag > newDrag && newDrag < MIN_DRAG) {
            drag = 0;
        } else {
            drag = newDrag;
        }
    }
    
    /**
     * Shoots the cueball in the given direction.
     * @param direction the direction for the shot
     */
    public boolean shoot(Vector2 direction) {
        if (drag > MIN_DRAG) {
            ball.applyImpulse(direction, drag * 25);
            drag = 0;
            this.setVisible(false);
            return true;
        }
        return false;
    }

    /**
     * Return the power or drag between min and max, on a scale 0 to 1.
     * @return normalised power
     */
    public float getNormPower() {
        float snappedDrag = Math.max(drag, MIN_DRAG);
        return (snappedDrag - MIN_DRAG) / (MAX_DRAG - MIN_DRAG);
    }
}
    
