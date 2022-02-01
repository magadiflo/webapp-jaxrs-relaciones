package org.magadiflo.webapp.jaxws.repositories;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.magadiflo.webapp.jaxws.models.Curso;

import java.util.List;

@RequestScoped
public class CursoRepositoryImpl implements CursoRepository {

    @Inject
    private EntityManager em;

    @Override
    public List<Curso> listar() {
        return this.em.createQuery("SELECT c FROM Curso AS c LEFT OUTER JOIN FETCH c.instructor", Curso.class).getResultList();
    }

    @Override
    public Curso guardar(Curso curso) {
        if (curso.getId() != null && curso.getId() > 0) {
            this.em.merge(curso);
        } else {
            this.em.persist(curso);
        }
        return curso;
    }

    @Override
    public Curso porId(Long id) {
        //return this.em.find(Curso.class, id);
        return this.em.createQuery("SELECT c FROM Curso AS c LEFT OUTER JOIN FETCH c.instructor WHERE c.id = :id", Curso.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void eliminar(Long id) {
        Curso curso = this.porId(id);
        this.em.remove(curso);
    }

}
