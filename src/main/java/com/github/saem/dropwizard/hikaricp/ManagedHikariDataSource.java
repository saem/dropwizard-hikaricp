package com.github.saem.dropwizard.hikaricp;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.dropwizard.db.ManagedDataSource;


public class ManagedHikariDataSource extends HikariDataSource implements ManagedDataSource {

    public ManagedHikariDataSource(final HikariConfig config) {
        super(config);
    }

    @Override
    public void start() throws Exception {
        // no op
    }

    @Override
    public void stop() throws Exception {
        close();
    }
}
