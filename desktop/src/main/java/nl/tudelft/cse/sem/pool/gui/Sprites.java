package nl.tudelft.cse.sem.pool.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import lombok.Generated;

@Generated
// In order to be hidden from the test coverage report
// Package GUI is excluded for test coverage as allowed, but still shows up in the report otherwise
public class Sprites {

    /**
     * Load sprite of cue ball.
     * @return Sprite of cue ball
     */
    public Sprite getCueBall() {
        String resource = "desktop/assets/sprite/cueball.png";
        return loadSprite(resource);
    }

    /**
     * Load sprite of number ball.
     * @param ball number of ball
     * @return Sprite of number ball
     */
    public Sprite getBallSprite(int ball) {
        String resource = "desktop/assets/sprite/ball/ball_" + ball + ".png";
        return loadSprite(resource);
    }

    /**
     * Load sprite of pool table.
     * @return Sprite of the pool table
     */
    public Sprite getPoolTableSprite() {
        String resource = "desktop/assets/sprite/poolBackground.png";
        return loadSprite(resource);
    }
    
    /**
     * Load sprite of cue.
     * @return Sprite of the cue
     */
    public Sprite getCueSprite() {
        String resource = "desktop/assets/sprite/cue.png";
        return loadSprite(resource);
    }

    /**
     * Load a sprite.
     * @param resource filepath of sprite
     * @return Sprite that has been loaded
     */
    public Sprite loadSprite(String resource) {
        Texture texture = new Texture(resource);
        return new Sprite(texture);
    }

}
