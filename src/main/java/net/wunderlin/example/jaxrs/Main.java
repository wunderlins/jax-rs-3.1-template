package net.wunderlin.example.jaxrs;

import static jakarta.ws.rs.SeBootstrap.Configuration.FREE_PORT;

import java.util.Optional;
import java.util.Set;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.SeBootstrap;
import jakarta.ws.rs.core.Application;

public class Main {
    public static final void main(String[] args) throws InterruptedException {
        // read property from command line -DPORT=xxxx, if not present, set a random port
        var intendedPort = Optional.ofNullable(System.getProperty("PORT")).map(Integer::valueOf).orElse(FREE_PORT);
        // optional: "0.0.0.0" means bind on any interface
        // default is "127.0.0.1", so the server is never accessible from remote
        var intendedHost = Optional.ofNullable(System.getProperty("HOST")).map(String::valueOf).orElse("127.0.0.1");

        // create a configuration builder for jax-rs
        var intendedConfig = SeBootstrap.Configuration.builder();

        // set our port and bind interface
        intendedConfig.port(intendedPort);
        intendedConfig.host(intendedHost);

        // start application with a callback for display the servers 
        // host:port configuration
        SeBootstrap.start(RestApplication.class, intendedConfig.build()).thenAccept(instance -> {
            var actualConfig = instance.configuration();
            System.out.println("Server is Running on: " + actualConfig.baseUri());
            System.out.println("CTRL+C to shut down.");
        });

        // wait for thread, this starts our initinite loop
        Thread.currentThread().join();
    }

    @ApplicationPath("/")
    public static class RestApplication extends Application {
        public Set<Class<?>> getClasses() {
            return Set.of(HelloWorldResource.class);
        }
    }

    @Path("/")
    public static class HelloWorldResource {

        @GET
        public Greeting greetings(@QueryParam("greeting") String greeting) {
            return new Greeting(greeting);
        }

    }

    record Greeting(String message) {}

}
