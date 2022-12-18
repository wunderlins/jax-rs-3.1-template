package net.wunderlin.example.jaxrs;

//import static jakarta.ws.rs.SeBootstrap.Configuration.FREE_PORT;

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
        SeBootstrap.start(new RestApplication());
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

    record Greeting(String message) {
    }

    /*
    public static final void main(String[] args) throws InterruptedException {
        var intendedPort = Optional.ofNullable(System.getProperty("PORT")).map(Integer::valueOf).orElse(FREE_PORT);
        var intendedConfig = SeBootstrap.Configuration.builder();
        intendedConfig.port(intendedPort);
        SeBootstrap.start(HelloWorldApplication.class, intendedConfig.build()).thenAccept(instance -> {
            var actualConfig = instance.configuration();
            System.out.println("BaseURI: " + actualConfig.baseUri());
        });
        Thread.currentThread().join();
    }

    @ApplicationPath("/")
    public static class HelloWorldApplication extends Application {
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

    record Greeting(String message) {
    }
*/
}
