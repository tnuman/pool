package nl.tudelft.cse.sem.pool.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import lombok.Generated;
import nl.tudelft.cse.sem.pool.gameobjects.Cue;
import nl.tudelft.cse.sem.pool.matchstate.Match;
import nl.tudelft.cse.sem.pool.matchstate.TurnState;

/**
 * Input processor when using the cue.
 */
@Generated
// In order to be hidden from the test coverage report
// Package GUI is excluded for test coverage as allowed, but still shows up in the report otherwise
public class CueInputHandler implements InputProcessor {

    private transient Cue cue;
    private transient Match match;
    private transient Stage stage;
    private transient float lastDistance;

    /**
     * Constructor for CueInputHandler.
     * @param cue cue
     * @param match match
     * @param stage stage
     */
    public CueInputHandler(Cue cue, Match match, Stage stage) {
        this.cue = cue;
        this.match = match;
        this.stage = stage;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            if (match.getTurnState().equals(TurnState.CUE)) {
                Vector2 direction = getBallPosition().cpy().sub(getPosition(screenX, screenY));
                if (cue.shoot(direction)) {
                    match.ballShot();
                }
                lastDistance = 0;
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (cue.isVisible()) {
            float newDistance = getPosition(screenX, screenY).dst(getBallPosition());
            float distanceChange = newDistance - lastDistance;
            cue.changeDrag(distanceChange * 0.005f);
            lastDistance = newDistance;
        }
        return false;
    }

    private Vector2 getPosition(int screenX, int screenY) {
        return new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
    }

    private Vector2 getBallPosition() {
        Vector3 ballPos =
                stage.getCamera().project(new Vector3(cue.getBallPos(), 0));
        return new Vector2(ballPos.x, ballPos.y);
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
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
