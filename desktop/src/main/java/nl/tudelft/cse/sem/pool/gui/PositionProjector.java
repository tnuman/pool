package nl.tudelft.cse.sem.pool.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import lombok.AllArgsConstructor;

/**
 * Translates screen positions to world positions.
 */
@AllArgsConstructor
public class PositionProjector {

    private transient Stage stage;

    public Vector2 getWorldPosition(int screenX, int screenY) {
        return getWorldPosition(new Vector3(screenX, screenY, 0));
    }

    public Vector2 getWorldPosition(Vector3 screenPosition) {
        Vector3 mouse = stage.getCamera().unproject(screenPosition);
        return new Vector2(mouse.x, mouse.y);
    }

    public Vector2 mouseWorldPosition() {
        Vector3 mouseScreen = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        return getWorldPosition(mouseScreen);
    }

}
