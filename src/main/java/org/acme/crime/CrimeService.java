package org.acme.crime;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.acme.serialKiller.SerialKiller;

import java.util.List;

@ApplicationScoped
public class CrimeService {

    public List<CrimeSimpleDTO> listAvailable(Boolean archived) {
        List<Crime> crimes = Crime.list("arquivado", archived);
        return CrimeSimpleDTO.fromList(crimes);
    }

    public Response findById(long id) {
        Crime crime = Crime.findById(id);

        if (crime == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Crime com o id " + id + " não encontrado.")
                    .build();
        }

        crime.vitimas.size();

        return Response.ok(CrimeDetailsDTO.fromEntity(crime)).build();
    }

    @Transactional
    public Response create(CrimeCreateDTO dto) {
        if (dto.serialKillerId() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Um serial killer deve ser informado")
                    .build();
        }

        SerialKiller sk = SerialKiller.findById(dto.serialKillerId());

        if (sk == null || !Boolean.TRUE.equals(sk.ativo)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Um serial killer ativo deve ser selecionado")
                    .build();
        }

        Crime crime = dto.toEntity(sk);
        crime.persist();

        return Response.status(Response.Status.CREATED).entity("Crime incluído").build();
    }

    @Transactional
    public Response edit(long id, CrimeCreateDTO crime) {
        Crime findCrime = Crime.findById(id);

        SerialKiller serialKiller = SerialKiller.findById(crime.serialKillerId());

        if (findCrime == null) {
            return  Response.status(Response.Status.NOT_FOUND)
                    .entity("Crime com o id " + id + " não encontrado.")
                    .build();
        }

        if (serialKiller  == null) {
            return  Response.status(Response.Status.NOT_FOUND)
                    .entity("Serial Killer não encontrado")
                    .build();
        }

        findCrime.nome = crime.nome();
        findCrime.descricao = crime.descricao();
        findCrime.data = crime.data();
        findCrime.local = crime.local();
        findCrime.arquivado = crime.arquivado();
        findCrime.serialKiller = serialKiller;

        findCrime.persist();
        return Response.accepted().build();
    }

    @Transactional
    public Response softDelete(long id) {
        Crime findCrime = Crime.findById(id);

        if (findCrime == null || findCrime.arquivado) {
            Response.status(Response.Status.NOT_FOUND)
                    .entity("Crime com o id " + id + " não encontrado ou já arquivado")
                    .build();
        }

        findCrime.arquivado = true;

        return Response.noContent().build();

    }
}
