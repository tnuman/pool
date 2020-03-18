package nl.tudelft.cse.sem.pool;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import nl.tudelft.cse.sem.pool.gui.PoolGame;

public class DesktopLauncher {

    /**
     * Main method for our game, starts the application.
     * @param arg - command line arguments
     */
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(840, 670);
        new Lwjgl3Application(new PoolGame(), config);
    }
}
