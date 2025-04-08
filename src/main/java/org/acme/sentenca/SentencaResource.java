package org.acme.sentenca;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.serialKiller.SerialKillerCreateDTO;
import org.acme.serialKiller.SerialKillerService;
import org.acme.serialKiller.SerialKillerSimpleDTO;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/sentenca")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Sentença", description = "Dados da Sentença de um Serial Killers")

public class SentencaResource {

        @Inject
        SentencaService sentencaService;

        @POST
        @Operation(summary = "Adiciona uma sentença a um Sk")
        public Response create(@Valid SentencaDTO sentenca) {
            return sentencaService.create(sentenca);
        }
}
