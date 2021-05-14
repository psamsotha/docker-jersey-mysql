package org.example.resource.db;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);

    private final DataSource dataSource;

    public DatabaseManager() {
        Properties props = new Properties();
        try {
            logger.debug("Loading mysql.properties file.");
            props.load(DatabaseManager.class.getResourceAsStream("/mysql.properties"));
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load mysql.properties file", ex);
        }

        if (System.getenv("MYSQL_PASSWORD") == null) {
            logger.warn("MYSQL_PASSWORD env var not set.");
            throw new DatabaseException("MYSQL_PASSWORD env var not set");
        }
        props.setProperty("dataSource.password", System.getenv("MYSQL_PASSWORD"));

        HikariConfig config = new HikariConfig(props);
        dataSource = new HikariDataSource(config);
    }


    public DataSource getDataSource() {
       return dataSource;
    }
}
