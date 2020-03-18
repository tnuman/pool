package nl.tudelft.cse.sem.pool.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import nl.tudelft.cse.sem.pool.gameobjects.Cue;

/**
 * Shows the power ball when dragging the cue.
 */
public class PowerBar extends Actor {

    private static final float WIDTH = 1.8f;
    private static final float HEIGHT = 0.03f;

    private transient Cue cue;
    private transient Texture texture;

    /**
     * Constructor for power bar.
     * @param cue cue
     * @param y bottom y position
     * @param worldWidth world width
     */
    public PowerBar(Cue cue, float y, float worldWidth) {
        this.cue = cue;
        setWidth(WIDTH);
        setHeight(HEIGHT);
        float x = (worldWidth - WIDTH) / 2;
        setPosition(x, y);
        createTexture();
    }

    private void createTexture() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fillRectangle(0, 0, 1, 1);
        texture = new Texture(pixmap);
        pixmap.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth() * cue.getNormPower(), getHeight());
    }

}
