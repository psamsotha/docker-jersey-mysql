package org.example.resource;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;

import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {

    public static final String BASE_URI = "http://0.0.0.0:8080";

    public static Server startServer() {
        return JettyHttpContainerFactory.createServer(URI.create(BASE_URI), new JerseyConfig(), true);
    }

    public static void main(String[] args) throws Exception {
        final Server server = startServer();
        System.out.format("Jersey started and listening at %s ... %n", BASE_URI);
        server.join();
    }
}

