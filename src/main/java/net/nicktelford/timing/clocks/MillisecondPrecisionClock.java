package net.nicktelford.timing.clocks;

/**
 * A base implementation for {@link Clock}s with a precision of milliseconds.
 */
abstract class MillisecondPrecisionClock implements Clock {

    @Override
    public long currentTime() {
        return currentTimeMillis() / 1000;
    }
}
