package org.rhq.metrics.test;


import org.junit.rules.ExternalResource;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.Session;

public class MetricsSuiteContext extends ExternalResource {
    private Session session;

    public Session getSession() {
        return session;
    }

    private static String getKeyspace() {
        return System.getProperty("keyspace", "rhq");
    }

    @Override
    protected void before() throws Throwable {
        String nodeAddresses = System.getProperty("nodes", "127.0.0.1");
        Cluster cluster = new Cluster.Builder().addContactPoints(nodeAddresses.split(","))
        // Due to JAVA-500 and JAVA-509 we need to explicitly set the protocol to V3.
        // These bugs are fixed upstream and will be in version 2.1.3 of the driver.
                .withProtocolVersion(ProtocolVersion.V3).build();
        session = cluster.connect(getKeyspace());
    };

    @Override
    protected void after() {
        session.close();
    }

}
