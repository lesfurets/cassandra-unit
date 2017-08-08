package org.cassandraunit;

import com.datastax.driver.core.CloseFuture;
import org.cassandraunit.dataset.CQLDataSet;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/**
 * @author Marcin Szymaniuk
 * @author Jeremy Sevellec
 */
public abstract class AbstractCassandraUnit4CQLTestCase {

    private static final Logger log = LoggerFactory.getLogger(CQLDataLoader.class);

    private CassandraCQLUnit cassandraUnit;
    private boolean initialized = false;
    private Session session;
    private Cluster cluster;

    public AbstractCassandraUnit4CQLTestCase() {
        cassandraUnit = new CassandraCQLUnit(getDataSet());
    }

    public AbstractCassandraUnit4CQLTestCase(String configurationFileName) {
        cassandraUnit = new CassandraCQLUnit(getDataSet(), configurationFileName);
    }

	@Before
    public void before() throws Exception {
        if (!initialized) {
            cassandraUnit.before();
            session = cassandraUnit.session;
            cluster = cassandraUnit.cluster;
            initialized = true;
        }
    }

    @After
    public void after(){
        EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
    }

    public abstract CQLDataSet getDataSet();

    public Session getSession() {
        return session;
    }
}
