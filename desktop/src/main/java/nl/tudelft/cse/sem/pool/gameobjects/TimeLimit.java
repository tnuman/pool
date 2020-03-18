package nl.tudelft.cse.sem.pool.gameobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lombok.Getter;
import lombok.Setter;

public class TimeLimit {

    private static int limit = 30;

    @Setter private transient Timer timer;
    @Getter @Setter private transient int seconds;
    private transient List<TimeListener> listeners;

    public TimeLimit() {
        listeners = new ArrayList<>();
    }

    /**
     * Start the timer.
     */
    public void start() {
        seconds = limit + 1;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        }, 0, 1000);
    }

    /**
     * Ticks the timer a second, and checks if it must stop.
     */
    public void tick() {
        seconds--;
        if (seconds < 0) {
            timer.cancel();
            notifyStop();
        }
    }

    /**
     * Stop the timer.
     */
    public void stop() {
        timer.cancel();
    }

    /**
     * Add listener when timer stops.
     * @param listener listener
     * @return true if listener is added
     */
    public boolean addListener(TimeListener listener) {
        if (listener == null) {
            return false;
        }
        if (!listeners.contains(listener)) {
            listeners.add(listener);
            return true;
        }
        return false;
    }

    /**
     * Notify all listeners timer has stopped.
     */
    // Otherwise we can't use a for each loop
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public void notifyStop() {
        for (TimeListener listener : listeners) {
            listener.stopped();
        }
    }
}
