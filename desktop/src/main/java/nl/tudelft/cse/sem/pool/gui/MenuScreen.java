package nl.tudelft.cse.sem.pool.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import lombok.Generated;
import nl.tudelft.cse.sem.pool.api.ApiWrapper;

@Generated
// In order to be hidden from the test coverage report
// Package GUI is excluded for test coverage as allowed, but still shows up in the report otherwise
public class MenuScreen implements Screen {

    private transient Stage stage;
    private transient PoolGame game;
    private transient Skin skin;
    private transient Table table;

    private transient TextButton playGame;
    private transient TextButton leaderboard;
    private transient TextButton settings;
    private transient TextButton logOut;

    /**
     * setup for menu screen.
     *
     * @param game the game
     */
    public MenuScreen(PoolGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("desktop/assets/uiskin.json"));
        table = new Table();
        table.setFillParent(true);

        playGame = createButton("Play a Game", Color.LIGHT_GRAY);
        playGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                game.toMatch();
            }
        });
        leaderboard = createButton("Leaderboard", Color.LIGHT_GRAY);
        leaderboard.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                game.toLeaderboard();
            }
        });
        settings = createButton("Settings", Color.LIGHT_GRAY);
        settings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                game.toSettings();
            }
        });
        logOut = createButton("Log out", Color.LIGHT_GRAY);
        logOut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                ApiWrapper.logout();
                game.toLogin();
            }
        });
        Texture logo = new Texture("desktop/assets/sprite/logo.png");
        Image image = new Image(logo);
        image.setPosition(Gdx.graphics.getWidth() / 2f, 530, Align.center);
        image.setOrigin(image.getWidth() / 2, image.getHeight() / 2);

        table.center();
        stage.addActor(table);
        stage.addActor(image);
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
