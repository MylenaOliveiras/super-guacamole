package org.acme.vitima;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/vitima")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Vitima", description = "Dados de uma vitima")

public class VitimaResource {

    @Inject
    VitimaService vitimaService;

    @GET
    @Operation(summary = "Lista todas as vitimas")
    public List<VitimaDetailsDTO> listAll(){
        return vitimaService.listAll();

    }

    @POST
    @Operation(summary = "Inclui uma nova vítima")
    public Response add(@Valid VitimaCreateDTO vitima){
        return vitimaService.create(vitima);
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualiza os dados de uma vitima existente")
    public Response update(@PathParam("id") long id, @Valid VitimaCreateDTO vitima) {
        return vitimaService.atualizar(id, vitima);
    }


    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deleta vitima")
    public Response delete(@PathParam("id") long id) {
        return vitimaService.deletar(id);
    }
}
