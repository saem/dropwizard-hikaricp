package com.github.saem.dropwizard.hikaricp;

import com.codahale.metrics.MetricRegistry;
import io.dropwizard.db.DataSourceFactory;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class HikariDataSourceFactoryTest {

    @Test
    public void buildsAManagedDataSource() {
        final HikariDataSourceFactory factory = new HikariDataSourceFactory();
        factory.dataSourceClassName = "foo";
        factory.setDefaultTransactionIsolation(DataSourceFactory.TransactionIsolation.READ_COMMITTED);

        assertTrue(factory.build(Mockito.mock(MetricRegistry.class), "foo") instanceof DataSourceFactory);
    }
}