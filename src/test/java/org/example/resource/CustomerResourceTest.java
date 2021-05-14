package org.example.resource;

import java.sql.SQLException;

import javax.ws.rs.core.Response;

import org.example.resource.domain.Customer;
import org.example.resource.repository.CustomerRepository;
import org.example.resource.resource.CustomerResource;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.internal.inject.Binder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerResourceTest extends JerseyTest {

    private final CustomerRepository repo;

    public CustomerResourceTest() {
        repo = mock(CustomerRepository.class);
    }

    @Override
    public ResourceConfig configure() {
        final Binder repoBinder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(repo).to(CustomerRepository.class);
            }
        };
        return new ResourceConfig(CustomerResource.class)
                .register(repoBinder);
    }

    @Test
    public void testGetCustomer() throws SQLException {
        Customer customer = new Customer(1, "Stephen", "Curry");
        when(repo.getCustomer(1)).thenReturn(customer);

        Response res = target("customers")
                .path("1")
                .request().get();

        assertThat(res.readEntity(Customer.class)).isEqualTo(customer);
    }
}
