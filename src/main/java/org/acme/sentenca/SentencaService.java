package org.acme.sentenca;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.acme.serialKiller.SerialKiller;

@ApplicationScoped
public class SentencaService {

    @Transactional
    public Response create(SentencaDTO dto) {
        SerialKiller sk = SerialKiller.findById(dto.serialKillerId());

        if (sk == null || !Boolean.TRUE.equals(sk.ativo)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Serial Killer ativo não encontrado")
                    .build();
        }

        if (sk.sentenca != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Este serial killer já possui uma sentença.")
                    .build();
        }

        Sentenca s = new Sentenca();
        s.descricao = dto.descricao();
        s.data = dto.data();
        s.serialKiller = sk;
        s.persist();

        sk.sentenca = s;
        sk.persist();

        return Response.status(Response.Status.CREATED).entity("Sentença registrada com sucesso").build();
    }

}
