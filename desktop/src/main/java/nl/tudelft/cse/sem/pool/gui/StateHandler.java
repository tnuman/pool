package nl.tudelft.cse.sem.pool.gui;

import nl.tudelft.cse.sem.pool.matchstate.TurnState;

/**
 * Used as a connection between the match
 * and an handler which manages things for game state (now MatchScreen).
 */
public interface StateHandler {

    void updateInputMethod(TurnState state);

    void endGame();
}
