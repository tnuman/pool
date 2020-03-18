package nl.tudelft.cse.sem.pool.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Generated
// In order to be hidden from the test coverage report
// Package GUI is excluded for test coverage as allowed, but still shows up in the report otherwise
public class Sounds {

    private transient Sound pocket;
    private transient Sound wallsHit;
    private transient Sound ballsHit;
    private transient Sound poolShot;
    private transient Sound youWin;

    @Getter @Setter
    private static transient float volume = 1.0f;

    /**
     * Creates the sounds needed for the match.
     */
    public void createSounds() {
        pocket = Gdx.audio.newSound(Gdx.files.internal("desktop/assets/sound/pocket.mp3"));
        wallsHit = Gdx.audio.newSound(Gdx.files.internal("desktop/assets/sound/wallsHit.mp3"));
        ballsHit = Gdx.audio.newSound(Gdx.files.internal("desktop/assets/sound/ballsHit.mp3"));
        poolShot = Gdx.audio.newSound(Gdx.files.internal("desktop/assets/sound/poolShot.mp3"));
        youWin = Gdx.audio.newSound(Gdx.files.internal("desktop/assets/sound/youWin.mp3"));
    }

    /**
     * Plays the poolShot sound.
     */
    public void playPoolShot() {
        poolShot.play(volume);
    }

    /**
     * Plays the poolShot sound.
     */
    public void playWallsHit() {
        wallsHit.play(volume);
    }

    /**
     * Plays the poolShot sound.
     */
    public void playBallsHit() {
        ballsHit.play(volume);
    }

    /**
     * Plays the poolShot sound.
     */
    public void playPocket() {
        pocket.play(volume);
    }

    /**
     * Plays the poolShot sound.
     */
    public void playYouWin() {
        youWin.play(volume);
    }

    /**
     * Dispose of the sounds.
     */
    public void disposeSound() {
        pocket.dispose();
        wallsHit.dispose();
        ballsHit.dispose();
        poolShot.dispose();
        youWin.dispose();
    }
}
