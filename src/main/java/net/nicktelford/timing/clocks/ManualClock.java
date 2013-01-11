package net.nicktelford.timing.clocks;

import java.util.concurrent.TimeUnit;

/**
 * A {@link Clock} that has its timing manually advanced by calls to {@link
 * #tick()}.
 * <p/>
 * This {@link Clock} should be used as a mock whenever a {@link Clock} or
 * {@link TickingClock} is required.
 *
 * @todo evaluate thread-safety
 */
public class ManualClock
        extends MillisecondPrecisionClock
        implements TickingClock {

    private final long interval;
    private long current;

    /**
     * Create a ManualClock with the given {@code interval} of the given {@link
     * TimeUnit}.
     *
     * @param interval The amount to advance this {@link Clock} by when {@link
     *                 #tick()} is called.
     * @param intervalUnit The unit of time for the {@code interval}.
     */
    public ManualClock(final long interval, final TimeUnit intervalUnit) {
        this(0, TimeUnit.SECONDS, interval, intervalUnit);
    }

    /**
     * Create a ManualClock with the given {@code interval} of the given {@link
     * TimeUnit} starting at the given {@code initialTime} of the given {@code
     * TimeUnit}.
     *
     * @param initialTime The initial time of this {@link Clock}.
     * @param initialUnit The unit of time for the {@code initialTime}.
     * @param interval The amount to advance this {@link Clock} by when {@link
     *                 #tick()} is called.
     * @param intervalUnit The unit of time for the {@code interval}.
     */
    public ManualClock(final long initialTime,
                       final TimeUnit initialUnit,
                       final long interval,
                       final TimeUnit intervalUnit) {
        this.current = initialUnit.toMillis(initialTime);
        this.interval = intervalUnit.toMillis(interval);
    }

    @Override
    public void tick() {
        current += interval;
    }

    @Override
    public long currentTimeMillis() {
        return current;
    }
}
