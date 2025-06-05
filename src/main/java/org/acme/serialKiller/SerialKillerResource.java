package org.acme.serialKiller;

import io.smallrye.faulttolerance.api.RateLimit;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.idempotency.Idempotent;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import org.eclipse.microprofile.faulttolerance.Fallback;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Path("/serialKiller")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Serial Killers", description = "Dados de Serial Killers")
public class SerialKillerResource {

    @Inject
    SerialKillerService skService;

    @GET
    @Operation(summary = "Lista todos os serial killers ativos")
    @RateLimit(value = 5, window = 1, windowUnit = ChronoUnit.MINUTES)
    @Fallback(fallbackMethod = "fallbackGlobal")
    public List<SerialKillerSimpleDTO> listAll() {
        return skService.listAll();
    }

    public List<SerialKillerSimpleDTO> fallbackGlobal() {
        throw new WebApplicationException(
                Response.status(Response.Status.TOO_MANY_REQUESTS)
                        .entity("Rate limit excedido. Tente novamente mais tarde.")
                        .type(MediaType.TEXT_PLAIN)
                        .build()
        );
    }


    @POST
    @Operation(summary = "Adiciona um novo serial killer")
    @Idempotent(expireAfter = 300)
    public Response add(@Valid SerialKillerCreateDTO killer) {
        return skService.create(killer);
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualiza os dados de um serial killer existente")
    public Response update(@PathParam("id") long id, @Valid SerialKillerCreateDTO killer) {
        return skService.edit(id, killer);
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Busca um serial killer por ID, incluindo crimes e sentença")
    public Response findById(@PathParam("id") long id) {
        return skService.findById(id);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Marca um serial killer como inativo (soft delete), se não houver crimes vinculados")
    public Response softDelete(@PathParam("id") long id) {
        return skService.softDelete(id);
    }
}
