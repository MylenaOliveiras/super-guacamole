package org.acme.crime;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;


@Path("/crimes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Crimes", description = "Crimes relacionadas a Serial Killers")

public class CrimeResource {
    @Inject
    CrimeService crimeService;

    @GET
    @Operation(summary = "Listar todos os crimes disponíveis para consulta")
    public List<CrimeSimpleDTO> listAll(@QueryParam("archived") Boolean archived) {
        if (archived == null) archived = false;
        return crimeService.listAvailable(archived);
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Traz detalhes de crime")
    public Response findById(@PathParam("id") long id){
        return crimeService.findById(id);
    }

    @POST
    @Operation(summary = "Inclui um novo crime")
    public Response create(@Valid CrimeCreateDTO newCrime){
        return crimeService.create(newCrime);
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Edita as informações de um crime")
    public Response edit(@PathParam("id") long id, @Valid CrimeCreateDTO crime) {
        return crimeService.edit(id, crime);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Realiza o arquivamento de um crime")
    public Response delete(@PathParam("id") long id) {
        return crimeService.softDelete(id);
    }


}
