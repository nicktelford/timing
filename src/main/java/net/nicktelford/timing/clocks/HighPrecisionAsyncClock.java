package net.nicktelford.timing.clocks;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

/**
 * An {@link AsyncClock} for {@link HighPrecisionClock}s that provide a
 * nanosecond component.
 * <p/>
 * While the nanosecond component of the underlying {@link HighPrecisionClock}
 * is available (through {@link #currentNanos()}), it is not asynchronous; only
 * the millisecond portion of the underyling {@link Clock}s time is updated
 * asynchronously.
 * <p/>
 * Calls to compute the nanosecond precision of the time will, therefore,
 * perform worse than calls to compute the millisecond precision time. However,
 * since the millisecond portion is cached,
 */
public class HighPrecisionAsyncClock
        extends AsyncClock
        implements HighPrecisionClock {

    private final HighPrecisionClock underlying;

    public HighPrecisionAsyncClock(final HighPrecisionClock underlying,
                                   final long interval,
                                   final TimeUnit intervalUnit,
                                   final long expiry,
                                   final TimeUnit expiryUnit) {
        super(underlying, interval, intervalUnit, expiry, expiryUnit);
        this.underlying = underlying;
    }

    public HighPrecisionAsyncClock(final HighPrecisionClock underlying,
                                   final long expiry,
                                   final TimeUnit expiryUnit) {
        super(underlying, expiry, expiryUnit);
        this.underlying = underlying;
    }

    public HighPrecisionAsyncClock(final HighPrecisionClock underlying) {
        super(underlying);
        this.underlying = underlying;
    }

    public HighPrecisionAsyncClock() {
        this(HighPrecisionSystemClock.defaultInstance);
    }

    @Override
    public long currentNanos() {
        return underlying.currentNanos();
    }

    @Override
    public BigInteger currentTimeNanos() {
        return underlying.currentTimeNanos();
    }
}
