package org.example.resource.db;

import java.util.function.Supplier;

import javax.sql.DataSource;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseFeature implements Feature {

    @Override
    public boolean configure(FeatureContext context) {
        context.register(new DatabaseBinder());
        return false;
    }

    public static class DatabaseBinder extends AbstractBinder {

        @Override
        protected void configure() {
            bindFactory(new DataSourceSupplier())
                    .to(DataSource.class);
        }

        private static class DataSourceSupplier implements Supplier<DataSource> {

            private static final Logger logger = LoggerFactory.getLogger(DataSourceSupplier.class);

            private static final DatabaseManager dbManager = new DatabaseManager();

            @Override
            public DataSource get() {
                DataSource dataSource = dbManager.getDataSource();
                logger.debug("Datasource: {}", dataSource);
                return dataSource;
            }
        }
    }
}
