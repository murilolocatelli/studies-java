package com.example.service;

import com.example.model.Bitcoin;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@Path("/bitcoins")
@RegisterRestClient(configKey = "bitcoin-api")
public interface BitcoinService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Bitcoin> listar();

}
