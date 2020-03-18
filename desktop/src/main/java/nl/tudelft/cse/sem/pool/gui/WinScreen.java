package nl.tudelft.cse.sem.pool.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import lombok.Generated;
import lombok.Getter;
import nl.tudelft.cse.sem.pool.api.ApiWrapper;
import nl.tudelft.cse.sem.pool.matchstate.State;
import nl.tudelft.cse.sem.pool.models.Score;
import nl.tudelft.cse.sem.pool.models.User;

@Generated
// In order to be hidden in the test coverage reported,
// package GUI is excluded for test coverage as allowed, but still shows up in the report otherwise
public class WinScreen implements Screen {

    private transient Stage stage;
    private transient PoolGame game;
    private transient Skin skin;
    private transient int totalScore;

    private transient TextField username;
    private transient ApiWrapper api;
    private transient TextButton saveButton;


    /**
     * Setup the win screen.
     * @param game the game
     */
    public WinScreen(PoolGame game, State state, int turnsLeft, int ballsLeft) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("desktop/assets/uiskin.json"));

        api = new ApiWrapper();

        this.totalScore = turnsLeft + ballsLeft;

        // Buttons
        saveButton = createButton("Save", 270, Color.LIME);
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveScore();
            }
        });
        TextButton goToLeaderboard = createButton("Go to Leaderboard", 160, Color.DARK_GRAY);
        goToLeaderboard.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.toLeaderboard();
            }
        });
        TextButton rematch = createButton("Rematch", 80, Color.DARK_GRAY);
        rematch.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.toMatch();
            }
        });
       
        // Win & score information
        Label win = state == State.PLAYER1_WON
                ? createLabel("Player 1 won!", 540) : createLabel("Player 2 won!", 540);
        win.setFontScale(2);
        
        Label turnsLabel = createLabel("Number of turns needed: " + turnsLeft, 495);
        stage.addActor(turnsLabel);
        Label ballsLabel = createLabel("Number of balls you had left: " + ballsLeft, 470);
        stage.addActor(ballsLabel);
        Label line = createLabel("-------------------------------------", 445);
        stage.addActor(line);
        Label score = createLabel("Total score: " + totalScore, 420);
        stage.addActor(score);
        username = createTextfield("Enter name:", 340);

        stage.addActor(win);
        stage.addActor(username);
        stage.addActor(saveButton);
        stage.addActor(goToLeaderboard);
        stage.addActor(rematch);
    }

    private void saveScore() {
        api.submitScore(new Score(new User(username.getText(), ""), totalScore));
        saveButton.setTouchable(Touchable.disabled);
        saveButton.setText("Score saved!");
        saveButton.setColor(Color.GREEN);
    }
    
    /**
     * Creates a button.
     *
     * @param text text on the button
     * @param y the height of the button in the screen
     * @param color the color of the button
     * @return the created button.
     */
    private TextButton createButton(String text, float y, Color color) {
        var button = new TextButton("", skin);
        button.setPosition(Gdx.graphics.getWidth() / 2f - 150, y);
        button.setSize(300, 60);
        button.setColor(color);
        button.setText(text);
        return button;
    }

    /**
     * Creates a text label.
     * @param text the text to be projected on the screen
     * @param y the height of the text label in the screen
     * @return the created label.
     */
    private Label createLabel(String text, float y) {
        var label = new Label("", skin);
        label.setPosition(Gdx.graphics.getWidth() / 2f, y);
        label.setText(text);
        label.setAlignment(Align.center);
        return label;
    }

    /**
     * Creates a normal textfield.
     *
     * @param text shows text on the textfield if not clicked on or typed in
     * @param y the height of the textfield in the screen
     * @return the created textfield.
     */
    private TextField createTextfield(String text, float y) {
        var tf = new TextField("", skin);
        tf.setPosition(Gdx.graphics.getWidth() / 2f - 150, y);
        tf.setSize(300, 50);
        tf.setMessageText(text);
        return tf;
    }

    /**
     * Shows the screen.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Refreshes the screen.
     *
     * @param delta refresh rate for the screen
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(25 / 255f, 20 / 255f, 20 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
        Gdx.graphics.setTitle("Just Another Pool Game");

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            saveScore();
        }
    }

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
