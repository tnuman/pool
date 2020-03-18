package nl.tudelft.cse.sem.pool.gui;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import nl.tudelft.cse.sem.pool.gameobjects.TimeLimit;
import nl.tudelft.cse.sem.pool.matchstate.Match;
import nl.tudelft.cse.sem.pool.matchstate.Player;
import nl.tudelft.cse.sem.pool.matchstate.State;
import nl.tudelft.cse.sem.pool.models.User;

@Generated
// In order to be hidden from the test coverage report
// Package GUI is excluded for test coverage as allowed, but still shows up in the report otherwise
public class PoolGame extends Game {

    @Getter @Setter
    private transient User user;
    private static final String TITLE = "Just Another Pool Game";

    //I think it is good if we do all the screen switching in this class
    //For some switches it is necessary to not only set another screen,
    //but also set/change some other things, so it's is the best if this happens in 1 place

    /**
     * Switches the screen to the match screen and starts a match.
     */
    public void toMatch() {
        Player player1 = new Player(user);
        Player player2 = new Player(user);
        Sounds sounds = new Sounds();
        sounds.createSounds();
        MatchScreen matchScreen = new MatchScreen(this,
                new Match(player1, player2, new TimeLimit(), sounds), sounds);
        this.setScreen(matchScreen);
    }

    /**
     * Deletes user info and switches the screen to the login screen.
     */
    public void toLogin() {
        setUser(null);
        this.setScreen(new LoginScreen(this));
        Gdx.graphics.setTitle(TITLE);
    }

    /**
     * Switches the screen to the register screen.
     */
    public void toRegister() {
        this.setScreen(new RegisterScreen(this));
        Gdx.graphics.setTitle(TITLE);
    }

    /**
     * Switches the screen to the main menu.
     */
    public void toMenu() {
        this.setScreen(new MenuScreen(this));
        Gdx.graphics.setTitle(TITLE);
    }

    /**
     * Switches the screen to the settings menu.
     */
    public void toSettings() {
        this.setScreen(new SettingsScreen(this));
        Gdx.graphics.setTitle(TITLE);
    }

    /**
     * Switches the screen to the leaderboard.
     */
    public void toLeaderboard() {
        this.setScreen(new LeaderboardScreen(this));
        Gdx.graphics.setTitle(TITLE);
    }

    /**
     * Switches the screen to the win screen.
     */
    public void toWin(State endState, int turnsLeft, int ballsLeft) {
        this.setScreen(new WinScreen(this, endState, turnsLeft, ballsLeft));
        Gdx.graphics.setTitle(TITLE);
    }

    /**
     * Called when the {@link Application} is first created.
     */
    @Override
    public void create() {
        toLogin();
        this.setScreen(new RegisterScreen(this));
    }

    /**
     * Called when the {@link Application} should render itself.
     */
    @Override
    public void render() {
        super.render();
    }

    /**
     * Called when the {@link Application} is destroyed.
     * Preceded by a call to {@link #pause()}.
     */
    @Override
    public void dispose() {
    }
}
