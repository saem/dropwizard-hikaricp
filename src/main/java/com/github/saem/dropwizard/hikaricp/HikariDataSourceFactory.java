package com.github.saem.dropwizard.hikaricp;

import com.codahale.metrics.MetricRegistry;
import com.zaxxer.hikari.HikariConfig;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.util.Duration;

import javax.swing.text.html.Option;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.Properties;
/**
 * A factory for pooled {@link ManagedDataSource}s.
 * <p/>
 * <b>Configuration Parameters:</b>
 * <table>
 *     <tr>
 *         <td>Name</td>
 *         <td>Default</td>
 *         <td>Description</td>
 *     </tr>
 *     <tr>
 *         <td>{@code driverClass}</td>
 *         <td><b>REQUIRED</b></td>
 *         <td>The full name of the JDBC driver class.</td>
 *     </tr>
 *     <tr>
 *         <td>{@code driverClass}</td>
 *         <td><b>REQUIRED</b></td>
 *         <td>The full name of the DataSource class.</td>
 *     </tr>
 *     <tr>
 *         <td>{@code url}</td>
 *         <td><b>REQUIRED</b></td>
 *         <td>The URL of the server.</td>
 *     </tr>
 *     <tr>
 *         <td>{@code user}</td>
 *         <td><b>REQUIRED</b></td>
 *         <td>The username used to connect to the server.</td>
 *     </tr>
 *     <tr>
 *         <td>{@code password}</td>
 *         <td>none</td>
 *         <td>The password used to connect to the server.</td>
 *     </tr>
 *     <tr>
 *         <td>{@code abandonWhenPercentageFull}</td>
 *         <td>N/A</td>
 *         <td>
 *             This property is not supported with HikariCP.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>{@code alternateUsernamesAllowed}</td>
 *         <td>N/A</td>
 *         <td>
 *             This property is not supported with HikariCP.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>{@code commitOnReturn}</td>
 *         <td>N/A</td>
 *         <td>
 *             This property is not supported with HikariCP.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>{@code autoCommitByDefault}</td>
 *         <td>JDBC driver's default</td>
 *         <td>The default auto-commit state of the connections.</td>
 *     </tr>
 *     <tr>
 *         <td>{@code readOnlyByDefault}</td>
 *         <td>JDBC driver's default</td>
 *         <td>The default read-only state of the connections.</td>
 *     </tr>
 *     <tr>
 *         <td>{@code properties}</td>
 *         <td>none</td>
 *         <td>Any additional JDBC driver parameters.</td>
 *     </tr>
 *     <tr>
 *         <td>{@code defaultCatalog}</td>
 *         <td>none</td>
 *         <td>The default catalog to use for the connections.</td>
 *     </tr>
 *     <tr>
 *         <td>{@code defaultTransactionIsolation}</td>
 *         <td>JDBC driver default</td>
 *         <td>
 *             The default transaction isolation to use for the connections. Can be one of
 *             {@code none}, {@code default}, {@code read-uncommitted}, {@code read-committed},
 *             {@code repeatable-read}, or {@code serializable}.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>{@code useFairQueue}</td>
 *         <td>N/A</td>
 *         <td>
 *             This property is not supported with HikariCP.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>{@code initialSize}</td>
 *         <td>N/A</td>
 *         <td>
 *             This property is not supported with HikariCP.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>{@code minSize}</td>
 *         <td>10</td>
 *         <td>
 *             The minimum size of the connection pool. Note, it's recommended to make this equal to the maximum pool size.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>{@code maxSize}</td>
 *         <td>100</td>
 *         <td>
 *             The maximum size of the connection pool.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>{@code initializationQuery}</td>
 *         <td>none</td>
 *         <td>
 *             A custom query to be run when a connection is first created.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>{@code logAbandonedConnections}</td>
 *         <td>{@code false}</td>
 *         <td>
 *             This property is not supported with HikariCP.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>{@code logValidationErrors}</td>
 *         <td>N/A</td>
 *         <td>
 *             This property is not supported with HikariCP.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>{@code maxConnectionAge}</td>
 *         <td>30 Minutes</td>
 *         <td>
 *             Connections which have been open for longer than {@code maxConnectionAge} are
 *             closed when returned.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>{@code maxWaitForConnection}</td>
 *         <td>30 seconds</td>
 *         <td>
 *             If a request for a connection is blocked for longer than this period, an exception
 *             will be thrown.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>{@code minIdleTime}</td>
 *         <td>1 minute</td>
 *         <td>
 *             The minimum amount of time an connection must sit idle in the pool before it is
 *             eligible for eviction.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>{@code jdbc4Driver}</td>
 *         <td>{@code true}</td>
 *         <td>
 *             If true, will not use the validationQuery option below, if your driver is old, time to upgrade,
 *             or be sad and set this to false.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>{@code validationQuery}</td>
 *         <td>{@code SELECT 1}</td>
 *         <td>
 *             <b>This feature is not recommended if you have a JDBC driver version 4 or newer.</b>
 *
 *             The SQL query that will be used to validate connections from this pool before
 *             returning them to the caller or pool. If specified, this query does not have to
 *             return any data, it just can't throw a SQLException.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>{@code validationQueryTimeout}</td>
 *         <td>5000 ms</td>
 *         <td>
 *             This property is used to control how long HikariCP will wait to test for aliveness, minimum 1 second.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>{@code checkConnectionWhileIdle}</td>
 *         <td>N/A</td>
 *         <td>
 *             This property is not supported with HikariCP.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>{@code checkConnectionOnBorrow}</td>
 *         <td>N/A</td>
 *         <td>
 *             This property is not supported with HikariCP.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>{@code checkConnectionOnConnect}</td>
 *         <td>N/A</td>
 *         <td>
 *             This property is not supported with HikariCP.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>{@code checkConnectionOnReturn}</td>
 *         <td>N/A</td>
 *         <td>
 *             This property is not supported with HikariCP.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>{@code autoCommentsEnabled}</td>
 *         <td>N/A</td>
 *         <td>
 *             This property is not supported with HikariCP.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>{@code evictionInterval}</td>
 *         <td>N/A</td>
 *         <td>
 *             This property is not supported with HikariCP.
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>{@code validationInterval}</td>
 *         <td>N/A</td>
 *         <td>
 *             This property is not supported with HikariCP.
 *         </td>
 *     </tr>
 * </table>
 */
public final class HikariDataSourceFactory extends DataSourceFactory {
    public boolean jdbc4Driver = false;
    @NotNull
    public String dataSourceClassName;

    public ManagedDataSource build(MetricRegistry metricRegistry, String name) {
        final Properties properties = new Properties();
        this.getProperties().entrySet()
                .stream()
                .forEach(p -> properties.setProperty(p.getKey(), p.getValue()));

        final HikariConfig config = new HikariConfig(properties);

        config.setJdbcUrl(getUrl());
        config.setUsername(getUser());
        config.setPassword(getPassword());
        Optional.ofNullable(getAutoCommitByDefault()).ifPresent(config::setAutoCommit);
        Optional.ofNullable(getReadOnlyByDefault()).ifPresent(config::setReadOnly);
        config.setCatalog(getDefaultCatalog());
        final Optional<String> isolationLevel;

        switch (getDefaultTransactionIsolation()) {
            case NONE:
                isolationLevel = Optional.of("TRANSACTION_NONE");
                break;
            case READ_UNCOMMITTED:
                isolationLevel = Optional.of("TRANSACTION_READ_UNCOMMITTED");
                break;
            case READ_COMMITTED:
                isolationLevel = Optional.of("TRANSACTION_READ_COMMITTED");
                break;
            case REPEATABLE_READ:
                isolationLevel = Optional.of("TRANSACTION_REPEATABLE_READ");
                break;
            case SERIALIZABLE:
                isolationLevel = Optional.of("TRANSACTION_SERIALIZABLE");
                break;
            case DEFAULT:
            default:
                isolationLevel = Optional.empty();
        }
        isolationLevel.ifPresent(config::setTransactionIsolation);

        config.setMinimumIdle(getMinSize());
        config.setMaximumPoolSize(getMaxSize());
        config.setConnectionInitSql(getInitializationQuery());
        config.setMaxLifetime(getMaxConnectionAge().or(Duration.minutes(30)).toMilliseconds());
        config.setConnectionTimeout(getMaxWaitForConnection().toMilliseconds());
        config.setMinimumIdle((int) getMinIdleTime().toSeconds());
        config.setValidationTimeout(getValidationQueryTimeout().or(Duration.seconds(1)).toMilliseconds());
        config.setMetricRegistry(metricRegistry);
        config.setDataSourceClassName(dataSourceClassName);

        return new ManagedHikariDataSource(config);
    }
}

