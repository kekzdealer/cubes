package at.kurumi;

/**
 * Engine Time tracker.
 */
public class Timer {

    private double lastLoopTime;

    public void init() {
        lastLoopTime = getTime();
    }

    public double getTime() {
        return System.nanoTime() / 1_000_000_000.0;
    }

    public double getElapsedTime() {
        final var now = getTime();
        final var elapsedTime = now - lastLoopTime;
        lastLoopTime = now;
        return elapsedTime;
    }

    public double getLastLoopTime() {
        return lastLoopTime;
    }
}
