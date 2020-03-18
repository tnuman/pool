package nl.tudelft.cse.sem.pool.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.List;

import lombok.Generated;

import nl.tudelft.cse.sem.pool.api.ApiWrapper;
import nl.tudelft.cse.sem.pool.models.Score;


@Generated
// In order to be hidden from the test coverage report
// Package GUI is excluded for test coverage as allowed, but still shows up in the report otherwise
public class LeaderboardScreen implements Screen {

    private transient Stage stage;
    private transient PoolGame game;
    private transient Skin skin;

    private transient TextButton back;

    private transient ApiWrapper api = new ApiWrapper();

    private transient Table scoreTable;

    /**
     * Setup for leaderboard screen.
     *
     * @param game the game
     */
    public LeaderboardScreen(PoolGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("desktop/assets/uiskin.json"));

        Label label = new Label("", skin);
        label.setPosition(Gdx.graphics.getWidth() / 2f, 600);
        label.setFontScale(2);
        label.setText("Compete with your friends and get the lowest score!");
        label.setAlignment(Align.center);
        
        this.scoreTable = new Table();
        this.scoreTable.setFillParent(true);
        this.scoreTable.center();

        createBackButton("Back");
        top5Leaderboard();

        stage.addActor(back);
        stage.addActor(this.scoreTable);
        stage.addActor(label);
    }

    // Suppress warning because of the for-each loop
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    private void populateTable(List<Score> scores) {
        this.scoreTable.clearChildren();

        // Add headers
        var usernameHeaderLabel = new Label("Username:", this.skin);
        var scoreHeaderLabel = new Label("Score:", this.skin);
        this.scoreTable.add(usernameHeaderLabel).width(100);
        this.scoreTable.add(scoreHeaderLabel).width(100);
        this.scoreTable.row();

        for (var score : scores) {
            var usernameLabel = new Label(score.getUsername(), this.skin);
            var scoreLabel = new Label(String.valueOf(score.getUserScore()), this.skin);
            this.scoreTable.add(usernameLabel).width(100);
            this.scoreTable.add(scoreLabel).width(100);
            this.scoreTable.row();
        }
    }

    /**
     * Gets called when the back button got pressed.
     */
    private void backClicked() {
        game.setScreen(new MenuScreen(game));
    }

    /**
     * Creates a button that leads to the backClicked method when pressed.
     *
     * @param text text on the button
     */
    private void createBackButton(String text) {
        back = new TextButton(text, skin);
        back.setPosition(Gdx.graphics.getWidth() / 2f - 150, 145);
        back.setSize(300, 60);
        back.setColor(Color.LIGHT_GRAY);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                backClicked();
            }
        });
    }

    /**
     *  Displays the top 5 players with their scores.
     */
    private void top5Leaderboard() {
        var list = api.getTop5();
        populateTable(list);
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
