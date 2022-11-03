package com.example.controller;

import com.example.model.Usuario;
import com.example.repository.UsuarioRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/usuarios")
public class UsuarioController {

    //TODO: Pesquisar pq RequestScoped de Repository não está funcionando
    //TODO: Pesquisar pq RequestScoped do Rest Client não está funcionando
    //TODO: Pesquisar os goals na doc: generate-code / generate-code-tests

    @Inject
    UsuarioRepository usuarioRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @PermitAll
    public void post(Usuario usuario) {
        System.out.println("Thread: " + Thread.currentThread().getName());
        System.out.println("Controller: " + this.hashCode());
        System.out.println("Repository: " + this.usuarioRepository.hashCode());
        System.out.println("\n");

        usuario.setPassword(BcryptUtil.bcryptHash(usuario.getPassword()));
        usuario.setRole(usuario.validarUsername());

        this.usuarioRepository.persist(usuario);
    }

    @GET
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> listar() {
        return this.usuarioRepository.listAll();
    }

}
