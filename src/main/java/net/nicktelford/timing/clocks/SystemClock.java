package net.nicktelford.timing.clocks;

/**
 * Basic {@link Clock} implementation that provides access to the standard JVM
 * system clock.
 * <p/>
 * <h2>Accuracy</h2>
 * The best possible accuracy of this {@link Clock} is determined by the JVM
 * itself and the host operating system.
 * <p/>
 * The following is a summary of the accuracy of the standard JVM clock, though
 * this may change in future:
 * <ul>
 *     <li><b>UNIX (Linux/BSD/Mac OS): 1 millisecond</b></li>
 *     <li><b>Windows: 10 milliseconds</b></li>
 * </ul>
 */
public class SystemClock extends MillisecondPrecisionClock implements Clock {

    /**
     * The default instance of the system {@link Clock}.
     */
    public static final Clock defaultInstance = new SystemClock();


    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
