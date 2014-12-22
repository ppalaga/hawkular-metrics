package org.rhq.metrics.impl.cassandra;

import static org.joda.time.DateTime.now;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.joda.time.DateTime;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.rhq.metrics.test.MetricsSuiteContext;

import com.datastax.driver.core.Session;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.Uninterruptibles;

@RunWith(Suite.class)
@SuiteClasses({DataAccessTest.class, MetricsServiceCassandraTest.class})
public class CoreMetricsSuite {

    @org.junit.ClassRule
    public static MetricsSuiteContext context = new MetricsSuiteContext();

    public static MetricsSuiteContext getContext() {
        return context;
    }

    public static Session getSession() {
        return context.getSession();
    }

    private static final long FUTURE_TIMEOUT = 3;

    public static <V> V getUninterruptibly(ListenableFuture<V> future) throws ExecutionException, TimeoutException {
        return Uninterruptibles.getUninterruptibly(future, FUTURE_TIMEOUT, TimeUnit.SECONDS);
    }

    public static DateTime hour0() {
        DateTime rightNow = now();
        return rightNow.hourOfDay().roundFloorCopy().minusHours(
            rightNow.hourOfDay().roundFloorCopy().hourOfDay().get());
    }

    public static DateTime hour(int hourOfDay) {
        return hour0().plusHours(hourOfDay);
    }
}
