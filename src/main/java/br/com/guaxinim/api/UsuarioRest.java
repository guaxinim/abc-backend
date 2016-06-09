package br.com.guaxinim.api;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

@Path("/usuario")
public class UsuarioRest {

    @GET
    @Produces("text/plain")
    public String getClichedMessage() {

        return "Hello World";
    }
}
