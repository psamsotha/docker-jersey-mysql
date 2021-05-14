package org.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("hello")
public class HelloResource {

    static final String MSG = "Hello Docker!" + System.getProperty("line.separator");

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return MSG;
    }
}
