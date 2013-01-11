package net.nicktelford.timing.clocks;

/**
 * Interface for implementations of a monotonic clock.
 * <p/>
 * All implementations <i>must</i> be monotonic and <i>should</i> be
 * thread-safe.
 */
public interface Clock {

    /**
     * The current time of this {@link Clock} in milliseconds since the
     * beginning of the UNIX epoch: Jan 1st 1970 00:00:00.00000 UTC.
     *
     * @return the UNIX timestamp of the current time in milliseconds.
     */
    public long currentTimeMillis();

    /**
     * The current time of this {@link Clock} in seconds since the beginning of
     * the UNIX epoch: Jan 1st 1970 00:00:00 UTC.
     *
     * @return the UNIX timestamp of the current time in seconds.
     */
    public long currentTime();
}
