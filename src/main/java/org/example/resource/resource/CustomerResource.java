package org.example.resource.resource;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.example.resource.domain.Customer;
import org.example.resource.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("customers")
public class CustomerResource {

    private static final Logger logger = LoggerFactory.getLogger(CustomerResource.class);

    private final CustomerRepository repository;

    @Inject
    public CustomerResource(CustomerRepository repository ) {
        this.repository = repository;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@PathParam("id") int id) {
        Customer customer;
        try {
            customer = repository.getCustomer(id);
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            return Response.serverError()
                    .entity(new ErrorResponse(500, "Problem getting data"))
                    .build();
        }
        if (customer == null) {
            return Response.status(404)
                    .entity(new ErrorResponse(404, "Could not find customer"))
                    .build();
        }
        return Response.ok(customer).build();
    }

    private static class ErrorResponse {

        private final int code;
        private final String message;

        public ErrorResponse(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
