package nl.tudelft.cse.sem.pool.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import lombok.Generated;
import nl.tudelft.cse.sem.pool.api.ApiWrapper;
import nl.tudelft.cse.sem.pool.models.User;


@Generated
// In order to be hidden from the test coverage report
// Package GUI is excluded for test coverage as allowed, but still shows up in the report otherwise
public class RegisterScreen implements Screen {

    private transient Stage stage;
    private transient PoolGame game;
    private transient Skin skin;

    private transient TextButton register;
    private transient TextButton login;
    private transient TextField username;
    private transient TextField password;
    private transient TextField confirmPassword;
    private transient Label authentication;
    private transient ApiWrapper api;

    /**
     * Setup for register screen.
     *
     * @param game the game
     */
    public RegisterScreen(PoolGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("desktop/assets/uiskin.json"));

        register = createButton("Register", 210, Color.LIME);
        createTextfield("username:", 400);
        password = new TextField("", skin);
        createPasswordField(password, "password:", 340);
        confirmPassword = new TextField("", skin);
        createPasswordField(confirmPassword, "confirm password:", 280);
        authentication = createLabel("", 150);
        Texture logo = new Texture("desktop/assets/sprite/logo.png");
        Image image = new Image(logo);
        image.setPosition(Gdx.graphics.getWidth() / 2f, 530, Align.center);
        image.setOrigin(image.getWidth() / 2, image.getHeight() / 2);
        login = createButton("Go to Login", 100, Color.DARK_GRAY);

        register.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                registerClicked();
            }
        });
        login.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                game.toLogin();
            }
        });

        stage.addActor(register);
        stage.addActor(username);
        stage.addActor(password);
        stage.addActor(confirmPassword);
        stage.addActor(authentication);
        stage.addActor(image);
        stage.addActor(login);

        // Create a new ApiWrapper
        api = new ApiWrapper();
    }

    /**
     * Gets called when the login button is pressed.
     */
    public void registerClicked() {
        if (!password.getText().equals(confirmPassword.getText())) {
            authentication.setText("Passwords don't match");
            return;
        }

        var user = new User(username.getText(), password.getText());
        if (this.api.signUp(user)) {
            System.out.println("Signup successful");
            game.toLogin();
        } else {
            // Reasons can be: empty user/pass, user already exists or server is down.
            authentication.setText("Signup not successful");
        }
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
     * Creates a normal textfield.
     *
     * @param text shows text on the textfield if not clicked on or typed in
     */
    public void createTextfield(String text, float y) {
        username = new TextField("", skin);
        username.setPosition(Gdx.graphics.getWidth() / 2f - 150, y);
        username.setSize(300, 50);
        username.setMessageText(text);
    }

    /**
     * Creates a password textfield.
     *
     * @param tf   the textfield field used for this textfield
     * @param text shows text on the textfield if not clicked on or typed in
     * @param y    height of the textfield
     */
    public void createPasswordField(TextField tf, String text, float y) {
        tf.setPosition(Gdx.graphics.getWidth() / 2f - 150, y);
        tf.setSize(300, 50);
        tf.setMessageText(text);
        tf.setPasswordCharacter('*');
        tf.setPasswordMode(true);
    }

    /**
     * Creates a text label.
     *
     * @param text the text to be projected on the screen
     * @param y the y coordinate for the label
     */
    public Label createLabel(String text, float y) {
        var label = new Label("", skin);
        label.setPosition(Gdx.graphics.getWidth() / 2f - 150, y);
        label.setSize(300, 50);
        label.setText(text);
        label.setAlignment(Align.center);
        return label;
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

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            registerClicked();
        }
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
