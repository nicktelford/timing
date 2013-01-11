package net.nicktelford.timing.clocks;

/**
 * A {@link Clock} that is advanced only when instructed to.
 * <p/>
 * The time reported by implementations must remain static until a call to
 * {@link #tick()} is made. Implementations may choose the amount by which to
 * advance the time on each {@link #tick()}.
 *
 * @see ManualClock
 * @see AsyncClock
 */
public interface TickingClock extends Clock {

    /**
     * Advance this {@link Clock}s time.
     */
    public void tick();
}
