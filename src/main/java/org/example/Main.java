package org.example;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {

    public static final String BASE_URI = "http://localhost:8080";

    public static Server startServer() {
        final ResourceConfig rc = new ResourceConfig().packages("org.example");
        return JettyHttpContainerFactory.createServer(URI.create(BASE_URI), rc, true);
    }

    public static void main(String[] args) throws Exception {
        final Server server = startServer();
        System.out.format("Jersey started and listening at %s ... %n", BASE_URI);
        server.join();
    }
}

