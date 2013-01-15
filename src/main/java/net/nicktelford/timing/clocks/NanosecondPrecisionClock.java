package net.nicktelford.timing.clocks;

import java.math.BigInteger;

/**
 * Base implementation for a {@link Clock} that provides nanosecond precision.
 * <p/>
 * While the <i>precision</i> is required to be nanoseconds, the <i>accuracy</i>
 * may be substantially more coarse.
 */
abstract class NanosecondPrecisionClock
        extends MillisecondPrecisionClock
        implements HighPrecisionClock {

    @Override
    public BigInteger currentTimeNanos() {
        return BigInteger.valueOf(currentTimeMillis())    // millis component
                .multiply(BigInteger.valueOf(1000000))    // scale to nanos
                .add(BigInteger.valueOf(currentNanos())); // add nanos component
    }
}
