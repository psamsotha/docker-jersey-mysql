package org.example.resource.repository;

import javax.inject.Singleton;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import org.glassfish.jersey.internal.inject.AbstractBinder;

public class RepositoryFeature implements Feature {

    @Override
    public boolean configure(FeatureContext context) {
        context.register(new RepositoryBinder());
        return false;
    }

    private static class RepositoryBinder extends AbstractBinder {

        @Override
        protected void configure() {
            bindAsContract(CustomerRepository.class)
                    .in(Singleton.class);
        }
    }
}
