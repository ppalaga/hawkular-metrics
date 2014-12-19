package org.rhq.metrics.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.rhq.metrics.core.Interval.parse;
import static org.rhq.metrics.core.Interval.Units.DAYS;
import static org.rhq.metrics.core.Interval.Units.HOURS;
import static org.rhq.metrics.core.Interval.Units.MINUTES;

import org.junit.Test;
import org.rhq.metrics.core.Interval.Units;


/**
 * @author John Sanda
 */
public class IntervalTest {

    @Test
    public void parseIntervals() {
        assertEquals(new Interval(15, MINUTES), parse("15min"));
        assertEquals(new Interval(1, HOURS), parse("1hr"));
        assertEquals(new Interval(1, DAYS), parse("1d"));

        /* Zero interval without units is illegal */
        assertParseFails("0");
        /* Zero length with units is legal and equivalent to NONE */
        assertEquals(Interval.NONE, parse("0min"));
        assertEquals(Interval.NONE, parse("0hr"));
        assertEquals(Interval.NONE, parse("0d"));

        /* empty string and are equivalent to NONE */
        assertEquals(Interval.NONE, parse(""));
        assertEquals(Interval.NONE, parse(null));

        /* Negative intervals are illegal */
        assertParseFails("-1d");

        assertParseFails("15minutes");
        assertParseFails("15 minutes");
        assertParseFails("min15");
        assertParseFails("12.2hr");
        assertParseFails("1d3min");
        assertParseFails("1d 3min");
    }

    private void assertParseFails(String interval) {
        try {
            parse(interval);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            /* expected */
        }
    }

    @Test
    public void newIntervals() {
        /* Zeros possible, equal to NONE */
        new Interval(0, Units.MINUTES);
        new Interval(0, Units.HOURS);
        new Interval(0, Units.DAYS);

        /* null units possible, equal to NONE */
        new Interval(0, null);

        /* Negative intervals are not possible */
        try {
            new Interval(-1, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            /* expected */
        }
        try {
            new Interval(-1, Units.MINUTES);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            /* expected */
        }
        try {
            new Interval(-1, Units.HOURS);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            /* expected */
        }
        try {
            new Interval(-1, Units.DAYS);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            /* expected */
        }
    }

    @Test
    public void equalsIntervals() {
        assertEquals(Interval.NONE, new Interval(0, null));
        assertEquals(new Interval(0, null), Interval.NONE);
    }

    @Test
    public void hashCodeContract() {
        assertEquals(new Interval(0, null), Interval.NONE);
        assertEquals(new Interval(0, Interval.Units.MINUTES), Interval.NONE);
        assertEquals(new Interval(0, Interval.Units.HOURS), Interval.NONE);
        assertEquals(new Interval(0, Interval.Units.DAYS), Interval.NONE);
    }
}
