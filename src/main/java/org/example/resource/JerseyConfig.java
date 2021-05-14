package org.example.resource;

import org.example.resource.db.DatabaseFeature;
import org.example.resource.repository.RepositoryFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        packages("org.example");

        register(DatabaseFeature.class);
        register(RepositoryFeature.class);
    }
}
