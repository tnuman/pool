package nl.tudelft.cse.sem.pool.gui;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import lombok.AllArgsConstructor;
import nl.tudelft.cse.sem.pool.gameobjects.CueBall;
import nl.tudelft.cse.sem.pool.matchstate.Match;

/**
 * Input processor when placing the white ball.
 */
@AllArgsConstructor
public class CueBallPlaceInputHandler implements InputProcessor {

    private transient CueBall ball;
    private transient Match match;
    private transient Stage stage;

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        ball.place();
        match.ballPlaced();
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        ball.setVisible(true);
        Vector2 mousePosition = new PositionProjector(stage).getWorldPosition(screenX, screenY);
        ball.setPosition(mousePosition.x, mousePosition.y);
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
