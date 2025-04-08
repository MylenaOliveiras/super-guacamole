package org.acme.serialKiller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.acme.crime.Crime;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SerialKillerService {

    public List<SerialKillerSimpleDTO> listAll() {
        return SerialKiller.findAll().list().stream()
                .map(obj -> (SerialKiller) obj)
                .map(SerialKillerSimpleDTO::fromEntity)
                .collect(Collectors.toList());
    }


    public Response findById(long id) {
        SerialKiller sk = SerialKiller.findById(id);
        if (sk == null) {
            throw new NotFoundException("Serial killer com o id " + id + " não encontrado.");
        }

        sk.crimes.size();

        return Response.ok(SerialKillerDetailsDTO.fromEntity(sk)).build();
    }

    @Transactional
    public Response create(SerialKillerCreateDTO dto) {
        SerialKiller killer = new SerialKiller();
        killer.nomeCompleto = dto.nomeCompleto();
        killer.alcunha = dto.alcunha();
        killer.nacionalidade = dto.nacionalidade();
        killer.ativo = true;

        killer.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @Transactional
    public Response edit(long id, SerialKillerCreateDTO dto) {
        SerialKiller foundKiller = SerialKiller.findById(id);

        if (foundKiller == null) {
            throw new NotFoundException("Serial Killer com o id " + id + " não encontrado.");
        }

        foundKiller.nomeCompleto = dto.nomeCompleto();
        foundKiller.alcunha = dto.alcunha();
        foundKiller.nacionalidade = dto.nacionalidade();



        SerialKillerCreateDTO teste = new SerialKillerCreateDTO(
                dto.nomeCompleto(),
                dto.alcunha(),
                dto.nacionalidade()
        );

        return Response.ok(teste).build();
    }

    @Transactional
    public Response softDelete(long id) {
        SerialKiller foundKiller = SerialKiller.findById(id);

        boolean hasCrimes = Crime.count("serialKiller.id", id) > 0;

        if (foundKiller == null) {
            throw new NotFoundException("Serial Killer não encontrado");
        } else if (hasCrimes) {
            return Response.status(Response.Status.CONFLICT)
                .entity("Não é possível deletar: este serial killer possui crimes vinculados.")
                .build();
        }


        foundKiller.ativo = false;
        return Response.ok("Serial killer arquivado com sucesso.").build();
    }

}
