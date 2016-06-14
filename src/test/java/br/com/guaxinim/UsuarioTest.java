package br.com.guaxinim;

import br.com.guaxinim.entities.Usuario;
import br.com.guaxinim.service.UsuarioService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.*;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.logging.Logger;

//@RunWith(Arquillian.class)
public class UsuarioTest {

    Logger log = Logger.getLogger(UsuarioServiceTest.class.getName());

    //@Deployment
    public static WebArchive createTestArchive() {
        return ShrinkWrap.create(WebArchive.class)
                .addClass(UsuarioService.class)
                .addClass(Usuario.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("wildfly-ds.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    //@PersistenceContext
    EntityManager entityManager;

    static Integer codigoUsuario;

    //@Before
    //@InSequence(1)
    public void inicio() {
        log.info("Iniciando testes");
        Assert.assertNotNull(entityManager);
    }

    //@Test
    //@InSequence(2)
    public void testInsertUsuario() {
        log.info("Test inserirUsuario()");
        Usuario u1 = new Usuario();
        u1.setNome("Arquillian User");
        u1.setCpf("12345678901");
        u1.setNascimento(new Date());
        u1.setTelefone("6181185744");
        u1.setObservacao("teste teste teste");
        entityManager.persist(u1);
        codigoUsuario = u1.getCodigoUsuario();
        log.info("Usuario " + codigoUsuario + " inserted");
    }

    //@Test
    //@InSequence(3)
    public void testGetUsuario() {
        log.info("Test getUsuario()");
        Assert.assertNotNull(codigoUsuario);
        Usuario u2 = entityManager.find(Usuario.class, codigoUsuario);
        Assert.assertNotNull(u2);
        Assert.assertEquals(u2.getNome(), "Arquillian User");
    }

    //@Test
    //@InSequence(4)
    public void testUpdateUsuario() {
        log.info("Test getUsuario()");
        Assert.assertNotNull(codigoUsuario);
        Usuario u3 = entityManager.find(Usuario.class, codigoUsuario);
        Assert.assertNotNull(u3);
        Assert.assertEquals(u3.getNome(), "Arquillian User");

        u3.setCpf("99988877766");
        entityManager.persist(u3);

        Usuario u4 = entityManager.find(Usuario.class, codigoUsuario);
        Assert.assertNotNull(u4);
        Assert.assertEquals(u4.getCpf(), "99988877766");
    }

    //@Test
    //@InSequence(5)
    public void testDeleteUsuario() {
        log.info("Test getUsuario()");
        Assert.assertNotNull(codigoUsuario);
        Usuario u5 = entityManager.find(Usuario.class, codigoUsuario);
        Assert.assertNotNull(u5);
        Assert.assertEquals(u5.getCpf(), "99988877766");
        entityManager.remove(u5);

        Usuario u6 = entityManager.find(Usuario.class, codigoUsuario);
        Assert.assertNull(u6);
    }

}
