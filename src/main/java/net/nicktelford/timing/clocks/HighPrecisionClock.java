package net.nicktelford.timing.clocks;

/**
 * A {@link Clock} that provides high-resolution timing data.
 */
public interface HighPrecisionClock extends Clock {

    /**
     * The nanosecond portion of the current time.
     * @return
     */
    public long currentNanos();
}
