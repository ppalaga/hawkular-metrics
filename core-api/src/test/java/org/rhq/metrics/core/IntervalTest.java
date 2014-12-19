package org.rhq.metrics.core;

import static org.rhq.metrics.core.Interval.Units.DAYS;
import static org.rhq.metrics.core.Interval.Units.HOURS;
import static org.rhq.metrics.core.Interval.Units.MINUTES;
import static org.rhq.metrics.core.Interval.parse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;


/**
 * @author John Sanda
 */
public class IntervalTest {

    @Test
    public void parseIntervals() {
        assertEquals(new Interval(15, MINUTES), parse("15min"));
        assertEquals(new Interval(1, HOURS), parse("1hr"));
        assertEquals(new Interval(1, DAYS), parse("1d"));

        assertExceptionThrown("15minutes");
        assertExceptionThrown("15 minutes");
        assertExceptionThrown("min15");
        assertExceptionThrown("12.2hr");
        assertExceptionThrown("1d3min");
        assertExceptionThrown("1d 3min");
    }

    private void assertExceptionThrown(String interval) {
        IllegalArgumentException exception = null;
        try {
            parse(interval);
        } catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

}
