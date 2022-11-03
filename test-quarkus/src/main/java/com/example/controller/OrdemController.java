package com.example.controller;

import com.example.model.Ordem;
import com.example.service.OrdemService;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@ApplicationScoped
@Path("/ordens")
public class OrdemController {

    @Inject
    OrdemService ordemService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @RolesAllowed("user")
    public void post(@Context SecurityContext securityContext, Ordem ordem) {
        System.out.println("Thread: " + Thread.currentThread().getName());
        System.out.println("Controller: " + this.hashCode());
        System.out.println("\n");

        this.ordemService.inserir(securityContext, ordem);
    }

    @GET
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ordem> listar() {
        return this.ordemService.listar();
    }

}
