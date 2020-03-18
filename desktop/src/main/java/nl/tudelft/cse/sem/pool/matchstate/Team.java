package nl.tudelft.cse.sem.pool.matchstate;

public enum Team {
    //Enumeration of the different teams a player/ball can have
    //Will be used to determine ball type when potted, or player team after potting the first ball
    UNDETERMINED, SOLID, STRIPES;

    /**
     * Returns the reverse of this team, undefined if undefined.
     * @return returns the reverse of this team, undefined if undefined
     */
    public Team reverse() {
        if (this == STRIPES) {
            return SOLID;
        } else if (this == SOLID) {
            return STRIPES;
        }
        return UNDETERMINED;
    }
}
