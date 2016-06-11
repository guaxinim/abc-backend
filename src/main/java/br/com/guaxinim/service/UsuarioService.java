package br.com.guaxinim.service;

import br.com.guaxinim.entities.Usuario;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;


@Stateless
public class UsuarioService {

    @PersistenceContext
    EntityManager entityManager;

    public void inserirUsuario(Usuario u) {
        entityManager.persist(u);
    }

    public Usuario getUsuario(Integer id) {
        return entityManager.find(Usuario.class, id);
    }

}
