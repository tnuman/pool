package nl.tudelft.cse.sem.pool.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import nl.tudelft.cse.sem.pool.creators.BodyCreator;
import nl.tudelft.cse.sem.pool.creators.CueBallPlacer;
import nl.tudelft.cse.sem.pool.creators.NumberBallPlacer;
import nl.tudelft.cse.sem.pool.gameobjects.Ball;
import nl.tudelft.cse.sem.pool.gameobjects.Cue;
import nl.tudelft.cse.sem.pool.gameobjects.CueBall;
import nl.tudelft.cse.sem.pool.gameobjects.NumberBall;
import nl.tudelft.cse.sem.pool.gameobjects.Table;
import nl.tudelft.cse.sem.pool.matchstate.Match;
import nl.tudelft.cse.sem.pool.matchstate.TurnState;



@Generated
// In order to be hidden from the test coverage report
// Package GUI is excluded for test coverage as allowed, but still shows up in the report otherwise
public class MatchScreen implements Screen, StateHandler {

    private static final float WORLD_WIDTH = 2;
    private transient float worldHeight = 1.5f;

    @Getter @Setter
    private static transient float musicVolume = 0.5f;

    private transient PoolGame game;
    private transient Match match;
    private transient Stage stage;
    private transient World world;
    private transient Table table;
    private transient CueBall cueBall;
    private transient Cue cue;
    private transient List<NumberBall> numberBalls;
    private transient Map<TurnState, InputProcessor> inputProcessors;
    private transient Sounds sounds;
    private transient Music music;
    private transient Box2DDebugRenderer debug;

    /**
     * Setup the match screen.
     *
     * @param game The pool game
     */
    public MatchScreen(PoolGame game, Match match, Sounds sounds) {
        this.game = game;
        this.match = match;
        match.setStateHandler(this);
        float dataBarHeight = WORLD_WIDTH
                * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()) - worldHeight;
        FitViewport viewport = new FitViewport(WORLD_WIDTH, worldHeight + dataBarHeight);
        stage = new Stage(viewport);
        world = new World(new Vector2(0, 0), true);
        World.setVelocityThreshold(0.1f);

        this.sounds = sounds;
        //At the moment the game is directly started
        //In the future, we might want to show a tutorial, or wait for a player to join etc.
        createGameElements();
        inputProcessors = new HashMap<>();
        inputProcessors.put(TurnState.CUE, new CueInputHandler(cue, match, stage));
        inputProcessors.put(TurnState.PLACE,
                new CueBallPlaceInputHandler(cueBall, match, stage));
        match.start();

        //debug = new Box2DDebugRenderer();
    }


    /**
     * Create all (visual) game elements.
     */
    //Otherwise it is not possible to use the for each loop
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    private void createGameElements() {
        // Create table
        table = new Table(world, 34f / 320f, 25f / 320f, 20f / 320f, 7f / 320f,
                WORLD_WIDTH, worldHeight, new Sprites(), stage);
        Sprites sprites = new Sprites();
        BodyCreator bodyCreator = new BodyCreator(world);
        // Create other balls
        NumberBallPlacer numberBallFactory = new NumberBallPlacer(sprites, bodyCreator, 1);
        numberBalls
                = numberBallFactory.placeNumberBalls(new Vector2(1.3f, worldHeight / 2),
                this.match, stage);

        // Create cueBall
        CueBallPlacer cueBallFactory = new CueBallPlacer(sprites, bodyCreator);
        cueBall = (CueBall) cueBallFactory.placeBall(new Vector2(0.5f, worldHeight / 2),
                this.match, stage);
        match.setCueBall(cueBall);
        match.setRemainingBalls(numberBalls);
        //Create cue
        cue = new Cue(cueBall, new Sprites());
        stage.addActor(cue);
        stage.addActor(new PowerBar(cue, 1.51f, WORLD_WIDTH));

        // Play music
        music = Gdx.audio.newMusic(Gdx.files.internal("desktop/assets/sound/music.mp3"));
        music.setVolume(musicVolume);
        music.setLooping(true);
        music.play();

        world.setContactListener(match.getWorldContactListener());
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        updateInputMethod(match.getTurnState());
    }

    public void updateInputMethod(TurnState state) {
        Gdx.input.setInputProcessor(inputProcessors.get(state));
        updateCue(state);
    }

    /**
     * Updates the visibility of the cue.
     */
    public void updateCue(TurnState state) {
        if (state.equals(TurnState.CUE)) {
            cue.setVisible(true);
        } else {
            cue.setVisible(false);
        }
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(25 / 255f, 20 / 255f, 20 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        cleanBodies();
        world.step(1 / 60f, 6, 2);
        match.check();
        Gdx.graphics.setTitle(match.getInfo() + " - " + "Time left: "
                + match.getTimer().getSeconds());
        //debug.render(world, stage.getCamera().combined);
    }

    /**
     * Removes bodies that needs to be destroyed.
     */
    //Otherwise we can't use a for each loop
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    private void cleanBodies() {
        if (!match.getNewlyPotted().isEmpty()) {
            for (Ball ball : match.getNewlyPotted()) {
                ball.remove();
                world.destroyBody(ball.getBody());
            }
            match.setNewlyPotted(new ArrayList<>());
        }
    }

    /**
     * Changes screen to the win screen, stops music.
     */
    public void endGame() {
        music.stop();
        //In case the black ball is potted first and the teams aren't assigned yet
        if (match.isAssignedTurn()) {
            game.toWin(match.getState(), match.getPlayerTurns(), match.getPlayerBallsSize());
        } else {
            game.toWin(match.getState(), match.getPlayerTurns(), 8);
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
        sounds.disposeSound();
        music.dispose();
    }

}
