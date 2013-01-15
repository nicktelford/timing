package net.nicktelford.timing.clocks;

import java.math.BigInteger;

/**
 * A {@link Clock} that provides high-resolution timing data.
 */
public interface HighPrecisionClock extends Clock {

    /**
     * The nanosecond portion of the current time.
     *
     * @return The nanosecond portion of the current time.
     */
    public long currentNanos();

    /**
     * This {@link Clock}s time in nanoseconds.
     *
     * @return the current time in nanoseconds.
     */
    public BigInteger currentTimeNanos();
}
