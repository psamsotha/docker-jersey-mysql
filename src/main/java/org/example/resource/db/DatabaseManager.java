package org.example.resource.db;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Class to manage DataSource.
 */
public class DatabaseManager {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);

    private DataSource dataSource;

    public DatabaseManager() {
        if (System.getenv("MYSQL_PASSWORD") == null) {
            throw new DatabaseException("MYSQL_PASSWORD env var not set");
        }
        Properties dataSourceProps = getProperties("/datasource.properties");
        dataSourceProps.setProperty("dataSource.password", System.getenv("MYSQL_PASSWORD"));
        HikariConfig config = new HikariConfig(dataSourceProps);

        Properties appProps = getProperties("/application.properties");
        int retries = Integer.parseInt(appProps.getProperty("databaseManager.startupRetries"));

        // With a multi-container startup, the database may not be ready
        // See https://docs.docker.com/compose/startup-order/
        logger.debug("Attempting to create DataSource...");
        do {
            if (loadDataSource(config)) {
                break;
            } else {
                logger.debug("Retrying to create data source: {} retries left.", retries);
                retries--;
                 try {
                     logger.debug("Waiting {} ms to retry", 1000);
                     Thread.sleep(1000);
                 } catch (InterruptedException ex) {
                     throw new RuntimeException(ex);
                 }
            }
        } while (retries != 0);

        if (dataSource == null) {
            throw new DatabaseException("DataSource could not be initialized.");
        }
    }

    private boolean loadDataSource(HikariConfig config) {
        try {
            dataSource = new HikariDataSource(config);
        } catch (Exception ex) {
            logger.info("Could not create data source: {}", ex.getMessage());
            return false;
        }
        return true;
    }

    private Properties getProperties(String resource) {
        Properties props = new Properties();
        try {
            logger.debug("Loading properties file: {}", resource);
            props.load(getClass().getResourceAsStream(resource));
        } catch (IOException ex) {
            logger.error("Could not load resource: {}", resource);
            throw new RuntimeException("Could not load resource: " + resource);
        }
        return props;
    }


    public DataSource getDataSource() {
       return dataSource;
    }
}
