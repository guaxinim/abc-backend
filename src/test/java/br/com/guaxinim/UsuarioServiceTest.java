package br.com.guaxinim;

import br.com.guaxinim.entities.Usuario;
import br.com.guaxinim.service.UsuarioService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.ShouldThrowException;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Date;
import java.util.logging.Logger;

@RunWith(Arquillian.class)
public class UsuarioServiceTest {

    Logger log = Logger.getLogger(UsuarioServiceTest.class.getName());

    @Deployment
    public static WebArchive createTestArchive() {
        return ShrinkWrap.create(WebArchive.class)
                .addClass(UsuarioService.class)
                .addClass(Usuario.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("wildfly-ds.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    UsuarioService usuarioService;

    static Integer codigoUsuario;

    @Before
    @InSequence(1)
    public void inicio() {
        log.info("Iniciando testes");
        Assert.assertNotNull(usuarioService);
    }

    @Test
    @InSequence(2)
    public void testInsertUsuario() {
        log.info("Test inserirUsuario()");
        Usuario u1 = new Usuario();
        u1.setNome("Arquillian User");
        u1.setCpf("12345678901");
        u1.setNascimento(new Date());
        u1.setTelefone("6181185744");
        u1.setObservacao("teste teste teste");
        usuarioService.inserirUsuario(u1);
        Assert.assertNotNull(u1.getCodigoUsuario());
        codigoUsuario = u1.getCodigoUsuario();
        log.info("Usuario " + codigoUsuario + " inserted");
    }

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test(expected = ValidationException.class)
    @InSequence(3)
    public void testValidationUsuario() {
        log.info("Test inserirUsuario()");
        Usuario u1 = new Usuario();
        u1.setTelefone("6181185744");
        u1.setObservacao("teste teste teste");
        thrown.expect(ConstraintViolationException.class);
        usuarioService.inserirUsuario(u1);
        Assert.assertNotNull(u1.getCodigoUsuario());
        codigoUsuario = u1.getCodigoUsuario();
        log.info("Usuario " + codigoUsuario + " inserted");
    }

    @Test
    @InSequence(4)
    public void testGetUsuario() {
        log.info("Test getUsuario()");
        Assert.assertNotNull(codigoUsuario);
        Usuario u2 = usuarioService.getUsuario(codigoUsuario);
        Assert.assertNotNull(u2);
        Assert.assertEquals(u2.getNome(), "Arquillian User");
    }

}
