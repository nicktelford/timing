package net.nicktelford.timing.clocks;

import java.util.concurrent.TimeUnit;

/**
 * A {@link Clock} whose current time is calculated asynchronously for improved
 * performance.
 * <p/>
 * A background {@link Thread} updates this {@link Clock}s state. The Thread is
 * configured with an {@code expiry} time, which controls how long it will
 * update the {@link Clock} for before stopping.
 * <p/>
 * Once the {@link Clock} has expired, requests for the time will first
 * synchronise the time and start the update {@link Thread}. For this reason,
 * periodically, the latency of requests for the current time will increase
 * slightly.
 * <p/>
 * The {@code interval} controls the frequency with which the time is updated.
 * The accuracy of this {@link Clock} cannot, therefore, be better than the
 * {@code interval} of the timer.
 */
public class AsyncClock
        extends MillisecondPrecisionClock
        implements TickingClock {

    private final Clock underlying;
    private final Thread tickThread;

    private long millis;

    /**
     * A {@link Thread} which advances the current state of the {@link
     * AsyncClock} to the current time of the underlying {@link Clock}.
     */
    class TickThread extends Thread {

        private final long expiry;
        private final long intervalMS;
        private final int intervalNanos;

        /**
         * Creates this {@link TickThread} with the given {@code interval} and
         * the given {@code expiry} period.
         *
         * @param interval The frequency with which to update the clock's time.
         * @param intervalUnit The unit of time for the {@code interval}.
         * @param expiry The length of time to continually update the clock
         *               before stopping.
         * @param expiryUnit The unit of time for the {@code expiry}.
         */
        public TickThread(final long interval,
                          final TimeUnit intervalUnit,
                          final long expiry,
                          final TimeUnit expiryUnit) {
            this.intervalMS = Math.max(1, intervalUnit.toMillis(interval));
            this.intervalNanos = (int) Math.min(999999, Math.max(0, 
                        intervalUnit.toNanos(interval) - (intervalMS * 1000000)));
            this.expiry = expiryUnit.toMillis(expiry);
            setDaemon(true);
        }

        /**
         * Updates the {@link AsyncClock}s current time from the {@code
         * underlying} {@link Clock}, sleeping for the configured {@code
         * interval} and stopping once the {@code expiry} time has elapsed.
         */
        @Override
        public void run() {
            final long initial = System.currentTimeMillis();
            do {
                tick();
                try {
                    Thread.sleep(intervalMS, intervalNanos);
                } catch (final java.lang.InterruptedException e) {
                    // tick
                }
            } while (millis  - initial < expiry);
        }
    }

    /**
     * Creates a new asynchronous {@link Clock} instance based on the default
     * {@link SystemClock}, with an {@code interval} of 1 millisecond and an
     * {@code expiry} time of 10 seconds.
     */
    public AsyncClock() {
        this(SystemClock.defaultInstance);
    }

    /**
     * Creates a new asynchronous {@link Clock} instance based on the given
     * underlying {@link Clock} instance, with an {@code interval} of 1
     * millisecond and an {@code expiry} time of 10 seconds.
     *
     * @param underlying The underlying {@link Clock} to asynchronously query
     *                   for the time.
     */
    public AsyncClock(final Clock underlying) {
        this(underlying, 10, TimeUnit.SECONDS);
    }

    /**
     * Creates a new asynchronous {@link Clock} instance based on the given
     * underlying {@link Clock} instance, with an {@code interval} of 1
     * millisecond and the given {@code expiry} time.
     *
     * @param underlying The underlying {@link Clock} to asynchronously query
     *                   for the time.
     * @param expiry The length of time to continually update this clock for.
     * @param expiryUnit The unit of time for the {@code expiry}.
     */
    public AsyncClock(final Clock underlying,
                      final long expiry,
                      final TimeUnit expiryUnit) {
        this(underlying, 1, TimeUnit.MILLISECONDS, expiry, expiryUnit);
    }

    /**
     * Creates a new asynchronous {@link Clock} instance based on the given
     * underlying {@link Clock} instance, with an {@code interval} of 1
     * millisecond and the given {@code expiry} time.
     *
     * @param underlying The underlying {@link Clock} to asynchronously query
     *                   for the time.
     * @param interval The length of time to wait between time updates. This
     *                 ultimately controls the best possible accuracy of this
     *                 {@link Clock}.
     * @param intervalUnit The unit of time for the {@code expiry}.
     * @param expiry The length of time to continually update this clock for.
     * @param expiryUnit The unit of time for the {@code expiry}.
     */
    public AsyncClock(final Clock underlying,
                      final long interval,
                      final TimeUnit intervalUnit,
                      final long expiry,
                      final TimeUnit expiryUnit) {
        this.underlying = underlying;
        tickThread = new TickThread(interval, intervalUnit, expiry, expiryUnit);
        tick();
        tickThread.setPriority(Thread.MAX_PRIORITY);
        tickThread.setDaemon(true);
        tickThread.start();
    }

    /**
     * Updates the time to the current time of the underlying {@link Clock}.
     *
     * @see TickingClock#tick()
     */
    @Override
    public void tick() {
        millis = underlying.currentTimeMillis();
    }

    /**
     * The current time of this {@link Clock} in milliseconds since the
     * beginning of the UNIX epoch: Jan 1st 1970 00:00:00.00000 UTC.
     * <p/>
     * If the background {@link Thread} for this {@code AsyncClock} is not
     * currently running, it will be first be started and the current time
     * synchronised, causing some invocations of this method to have higher
     * latency than most.
     *
     * @return the UNIX timestamp of the current time in milliseconds.
     */
    @Override
    public long currentTimeMillis() {
        if (!tickThread.isAlive()) {
            tick();
            tickThread.start();
        }

        return millis;
    }
}
