package nl.tudelft.cse.sem.pool.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import lombok.Generated;

@Generated
// In order to be hidden from the test coverage report
// Package GUI is excluded for test coverage as allowed, but still shows up in the report otherwise
public class SettingsScreen implements Screen {

    private transient Stage stage;
    private transient PoolGame game;
    private transient Skin skin;
    private transient Table table;

    private transient TextButton back;
    private transient TextButton sound;
    private transient TextButton music;

    /**
     * setup for settings screen.
     *
     * @param game the game
     */
    public SettingsScreen(PoolGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("desktop/assets/uiskin.json"));
        table = new Table();
        table.setFillParent(true);

        back = createButton("Back", Color.LIGHT_GRAY);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                game.toMenu();
            }
        });
        createSoundButton("Sound");
        createMusicButton("Music");

        table.center();
        stage.addActor(table);
    }

    /**
     * Creates a button.
     *
     * @param text text on the button
     * @param color the color of the button
     * @return the created button.
     */
    private TextButton createButton(String text, Color color) {
        var button = new TextButton("", skin);
        button.setColor(color);
        button.setText(text);
        table.add(button).width(120).height(60).expandX();
        return button;
    }

    /**
     * Gets called when the sound button got pressed.
     */
    private void soundClicked() {
        Sounds.setVolume(Sounds.getVolume() == 0 ? 1 : 0);
        setSoundButtonColor(Sounds.getVolume() == 0, sound);
    }

    /**
     * Creates a button that leads to the soundClicked method when pressed.
     *
     * @param text text on the button
     */
    private void createSoundButton(String text) {
        sound = new TextButton(text, skin);
        sound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundClicked();
            }
        });
        table.add(sound).width(120).height(60).expandX();
        setSoundButtonColor(Sounds.getVolume() == 0, sound);
    }

    private void setSoundButtonColor(boolean red, TextButton button) {
        if (red) {
            button.setColor(1,0,0,1);
        } else {
            button.setColor(0,1,0,1);
        }
    }

    /**
     * Gets called when the music button got pressed.
     */
    private void musicClicked() {
        MatchScreen.setMusicVolume(MatchScreen.getMusicVolume() == 0 ? 0.5f : 0);
        setSoundButtonColor(MatchScreen.getMusicVolume() == 0, music);
    }

    /**
     * Creates a button that leads to the musicClicked method when pressed.
     *
     * @param text text on the button
     */
    private void createMusicButton(String text) {
        music = new TextButton(text, skin);
        music.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                musicClicked();
            }
        });
        table.add(music).width(120).height(60).expandX();
        setSoundButtonColor(MatchScreen.getMusicVolume() == 0, music);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(25 / 255f, 20 / 255f, 20 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    /* We need to @Override the methods below to be able to implement the interface(s),
    Bodies can stay empty if we don't use them */

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
