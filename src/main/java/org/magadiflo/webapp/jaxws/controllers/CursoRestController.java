package org.magadiflo.webapp.jaxws.controllers;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.magadiflo.webapp.jaxws.models.Curso;
import org.magadiflo.webapp.jaxws.services.CursoService;

import java.util.List;
import java.util.Optional;

@RequestScoped
@Path("/cursos")
@Produces(MediaType.APPLICATION_JSON) //El formato que devuelve
public class CursoRestController {

    @Inject
    private CursoService service;

    /*Primera forma*/
    @GET
    public List<Curso> listar() {
        return this.service.listar();
    }

    /*Segunda forma*/
    /*
    @GET
    public Response listar() {
        return Response.ok(this.service.listar()).build();
    }
    */

    @GET
    @Path("/{id}")
    public Response porId(@PathParam("id") Long id) {
        Optional<Curso> optional = this.service.porId(id);
        if (optional.isPresent()) {
            return Response.ok(optional.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON) //El formato que recibe
    public Response crear(Curso curso) {
        try {
            Curso nuevoCurso = this.service.guardar(curso);
            return Response.ok(nuevoCurso).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editar(@PathParam("id") Long id, Curso curso) {
        Optional<Curso> cursoOptional = this.service.porId(id);
        if (cursoOptional.isPresent()) {
            Curso nuevoCurso = cursoOptional.get();
            nuevoCurso.setNombre(curso.getNombre());
            nuevoCurso.setDescripcion(curso.getDescripcion());
            nuevoCurso.setDuracion(curso.getDuracion());
            nuevoCurso.setInstructor(curso.getInstructor());
            try {
                this.service.guardar(nuevoCurso);
                return Response.ok(nuevoCurso).build();
            } catch (Exception e) {
                e.printStackTrace();
                return Response.serverError().build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) {
        Optional<Curso> cursoOptional = this.service.porId(id);
        if (cursoOptional.isPresent()) {
            try {
                this.service.eliminar(id);
                return Response.noContent().build();
            } catch (Exception e) {
                e.printStackTrace();
                return Response.serverError().build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
