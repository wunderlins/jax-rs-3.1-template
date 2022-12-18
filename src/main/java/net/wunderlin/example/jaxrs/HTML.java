package net.wunderlin.example.jaxrs;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Objects;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;

@Produces("text/html")
public class HTML implements Feature, MessageBodyWriter {

    @Override
    public boolean isWriteable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public void writeTo(Object t, Class type, Type genericType, Annotation[] annotations, MediaType mediaType,
            MultivaluedMap httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        new PrintStream(entityStream).printf("""
                <html>
                  <h1>%s</h1>
                  <form action=".">
                    <input type="text" id="greeting" name="greeting" value=""><br>
                    <input type="submit" value="Submit">
                  </form>
                </html>""",
                Objects.toString(t)).flush();
    }

    @Override
    public boolean configure(FeatureContext context) {
        return true;
    }

}
