package com.example.controller;

import com.example.model.Bitcoin;
import com.example.service.BitcoinService;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Path("/bitcoins")
public class BitcoinController {

    @Inject
    @RestClient
    BitcoinService bitcoinService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bitcoin> get() {
        System.out.println("Thread: " + Thread.currentThread().getName());
        System.out.println("Controller: " + this.hashCode());
        System.out.println("BitcoinService: " + this.bitcoinService.hashCode());
        System.out.println("\n");

        return this.bitcoinService.listar();
    }

}
