package net.nicktelford.timing.clocks;

import java.util.concurrent.atomic.AtomicLong;

// todo: is this even realistic and feasible?
// if it's generally not possible, we should remove this
/**
 * A {@link System} {@link Clock} implementation that yields a higher precision
 * time, but with no better accuracy.
 * <p/>
 * The extra precision provided by this {@link Clock} is guaranteed to be
 * monotonic, but not accurate; it won't reflect the <i>actual</i> number of
 * nanoseconds that have elapsed, but will provide an estimate.
 * <p/>
 * This should only be used when a higher level of monotonic precision is
 * required, but where the accuracy is unimportant; for example, in generation
 * of a version 1 UUID.
 */
public class HighPrecisionSystemClock
        extends MillisecondPrecisionClock
        implements HighPrecisionClock {

    private AtomicLong lastNanos = new AtomicLong(0);

    /**
     * An approximation for the number of nanoseconds into the current
     * millisecond this clock is at.
     *
     * @return the number of nanoseconds in to the current millisecond this
     *         clock is at.
     */
    public long currentNanos() {
        long oldNanos;
        long newNanos;

        do {
            oldNanos = lastNanos.get();
            newNanos = System.nanoTime();
        } while (!lastNanos.compareAndSet(oldNanos, newNanos));

        return oldNanos == 0 ? 0 : (newNanos - oldNanos) % 1000000;
        // by ignoring intervals >= 1msec, is it guaranteed to be monotonic?
        // it should be as successive calls to currentTimeMills() will yield
        // different answers when difference is >= 1msec
        // does this mean we're only monotonic if the underlying clocks'
        // currentTimeMills has a guaranteed accuracy of at least 1msec?
        // todo: investigate
    }

    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
