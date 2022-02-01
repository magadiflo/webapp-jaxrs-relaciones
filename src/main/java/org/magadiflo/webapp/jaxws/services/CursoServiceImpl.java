package org.magadiflo.webapp.jaxws.services;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.magadiflo.webapp.jaxws.models.Curso;
import org.magadiflo.webapp.jaxws.repositories.CursoRepository;

import java.util.List;
import java.util.Optional;

@Stateless
public class CursoServiceImpl implements CursoService {

    @Inject
    private CursoRepository repository;

    @Override
    public List<Curso> listar() {
        return this.repository.listar();
    }

    @Override
    public Curso guardar(Curso curso) {
        return this.repository.guardar(curso);
    }

    @Override
    public Optional<Curso> porId(Long id) {
        return Optional.ofNullable(this.repository.porId(id));
    }

    @Override
    public void eliminar(Long id) {
        this.repository.eliminar(id);
    }

}
