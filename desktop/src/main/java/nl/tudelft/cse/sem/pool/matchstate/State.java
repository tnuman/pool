package nl.tudelft.cse.sem.pool.matchstate;

public enum State {
    //Enumeration of the different states a pool match can have
    //Depending on the state, the match has to handle some events differently
    SETUP, PLAYER1_TURN, PLAYER2_TURN, PLAYER1_WON, PLAYER2_WON, ABORTED;

    /**
     * Change the turn to the other player's.
     * @return the state in which the other player has a turn
     */
    public State reverse() {
        if (this == PLAYER1_TURN) {
            return PLAYER2_TURN;
        } else if (this == PLAYER2_TURN) {
            return PLAYER1_TURN;
        }
        return this;
    }
}
