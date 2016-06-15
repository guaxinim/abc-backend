package br.com.guaxinim.api;
import br.com.guaxinim.entities.Usuario;
import br.com.guaxinim.service.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.util.function.Supplier;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/usuario")
public class UsuarioRest {

    Logger log = Logger.getLogger(UsuarioRest.class.getName());

    @EJB
    UsuarioService usuarioService;

    @GET
    @Path("{id}")
    @Produces("application/json")
    public String getUsuario(@PathParam("id") String id, @Context final HttpServletResponse response) {
        String json = "";
        try {
            Integer param = Integer.valueOf(id);
            Usuario u = usuarioService.getUsuario(param);
            try {
                json = new ObjectMapper().writeValueAsString(u);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } catch (NumberFormatException nfe) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return json;
    }
}
