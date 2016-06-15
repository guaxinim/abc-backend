package br.com.guaxinim.api;
import br.com.guaxinim.entities.Usuario;
import br.com.guaxinim.service.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/usuario")
public class UsuarioRest {

    Logger log = Logger.getLogger(UsuarioRest.class.getName());

    @EJB
    UsuarioService usuarioService;

    @GET
    @Produces("application/json")
    public String getUsuario() {
        String json = "";
        Usuario u = usuarioService.getUsuario(1);
        try {
            json = new ObjectMapper().writeValueAsString(u);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
